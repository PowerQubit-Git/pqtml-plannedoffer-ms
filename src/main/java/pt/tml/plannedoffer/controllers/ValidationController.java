package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.database.MongoDbService;
import pt.tml.plannedoffer.database.PostgresServiceProxy;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.models.PlannedOfferUpload;
import pt.tml.plannedoffer.models.ResponseMessage;
import pt.tml.plannedoffer.services.GtfsValidationService;

import javax.servlet.http.HttpServletRequest;

@Flogger
@RestController
@CrossOrigin("*")
public class ValidationController
{
    @Autowired
    MongoDbService mongoService;

    @Autowired
    PostgresServiceProxy postgresServiceProxy;

    @Autowired
    GtfsValidationService validatorService;

    @PostMapping("validate")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("offerPlanId") String offerPlanId, @RequestParam("user") String publisher, HttpServletRequest request) throws Exception
    {
        PlannedOfferUpload offerPlan;
        Binary zipFile;

//        var remoteIpAddress= request.getHeader("X-FORWARDED-FOR");
//        var remoteIpAddress= request.getRemoteAddress());

        if (ApplicationState.uploadBusy || ApplicationState.validationBusy || ApplicationState.entityPersistenceBusy)
        {
            log.atWarning().log(String.format("Validation request refused in busy state u:%s v:%s p:%s", ApplicationState.uploadBusy, ApplicationState.validationBusy, ApplicationState.entityPersistenceBusy));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("service busy"));
        }

        ApplicationState.validationBusy = true;

        //
        // Get zip from Mongo
        //
        try
        {
            offerPlan = mongoService.getPlan(offerPlanId);
            if (offerPlanId == null)
            {
                String errorString = String.format("given feed id is null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(errorString));
            }
            // check file content present
            if (offerPlan.getFile() == null)
            {
                String errorString = String.format("feed with id : %s not found", offerPlanId);
                log.atWarning().log(errorString);
                ApplicationState.validationBusy = false;
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage(errorString));
            }
        }
        catch (Exception e)
        {
            String errorString = String.format("error obtaining feed with id : %s", offerPlanId);
            log.atSevere().log(errorString);
            ApplicationState.validationBusy = false;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(errorString));
        }

        //
        // Validate the offer plan
        // (we pass the whole object including metadata info to the validator)
        //
        try
        {
            var feedContainer = validatorService.validatePlan(offerPlan);
        }
        catch (Exception e)
        {
            String errorString = String.format("error validating feed id : %s", offerPlanId);
            log.atSevere().log(errorString);
            ApplicationState.validationBusy = true;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(errorString));
        }


        //
        // Validation process completed
        // update plan with results
        //
        offerPlan.setValidationReport(validatorService.getValidationsReport());
        offerPlan.setErrorsReport(validatorService.getErrorsReport());
        offerPlan.setTableResumeList(validatorService.getSummaryList());

        //
        //  Save plan to MongoDb
        //  with added status and reports
        //
        mongoService.savePlan(offerPlan);

        GtfsFeedContainer gtfsFeedContainer = validatorService.getFeedContainer();
        if (gtfsFeedContainer.isParsedSuccessfully())
        {
            ApplicationState.validationBusy = false;
            ApplicationState.entityPersistenceBusy = true;
            // Persist entities to Postgres
            // Perform database functions like
            // - calculating frequencies
            // Async
            persistEntitiesToDatabase(gtfsFeedContainer, offerPlanId);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("validation completed"));
        }
        else
        {
            ApplicationState.validationBusy = false;
            ApplicationState.entityPersistenceBusy = false;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((new ResponseMessage("validation failed")));
        }
    }


    @Async
    @LogExecutionTime(started = "Persisting to DB", completed = "DB persist completed")
    public void persistEntitiesToDatabase(GtfsFeedContainer gtfsFeedContainer, String offerPlanId) throws Exception
    {
        // Persist entities to Postgres -asynchronously-
        postgresServiceProxy.persistAllEntities(offerPlanId, gtfsFeedContainer);
    }
}

