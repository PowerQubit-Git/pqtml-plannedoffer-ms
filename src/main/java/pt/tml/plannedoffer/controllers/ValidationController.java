package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.apache.commons.lang3.tuple.Pair;
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
import pt.tml.plannedoffer.models.Notice;
import pt.tml.plannedoffer.models.PlannedOfferUpload;
import pt.tml.plannedoffer.models.ResponseMessage;
import pt.tml.plannedoffer.gtfs.GtfsValidationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ValidationResult> uploadFile(@RequestParam("offerPlanId") String offerPlanId, @RequestParam("user") String publisher, HttpServletRequest request) throws Exception
    {
        PlannedOfferUpload offerPlan;
        Binary zipFile;

//        var remoteIpAddress= request.getHeader("X-FORWARDED-FOR");
//        var remoteIpAddress= request.getRemoteAddress());

        if (ApplicationState.uploadBusy || ApplicationState.validationBusy || ApplicationState.entityPersistenceBusy)
        {
            log.atWarning().log(String.format("Validation request refused in busy state u:%s v:%s p:%s", ApplicationState.uploadBusy, ApplicationState.validationBusy, ApplicationState.entityPersistenceBusy));
            var response = new ValidationResult();
            response.setMessage("Service Busy");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
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
                var response = new ValidationResult();
                response.setMessage("given feed id is null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            // check file content present
            if (offerPlan.getFile() == null)
            {
                String errorString = String.format("feed with id : %s not found", offerPlanId);
                log.atWarning().log(errorString);
                ApplicationState.validationBusy = false;
                var response = new ValidationResult();
                response.setMessage(errorString);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        }
        catch (Exception e)
        {
            String errorString = String.format("error obtaining feed with id : %s", offerPlanId);
            log.atSevere().log(errorString);
            ApplicationState.validationBusy = false;
            var response = new ValidationResult();
            response.setMessage(errorString);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
            var response = new ValidationResult();
            response.setMessage(errorString);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


        //
        // Validation process completed
        // update plan with results
        //
        offerPlan.setValidationReport(validatorService.getValidationsReport());
        offerPlan.setErrorsReport(validatorService.getErrorsReport());
        offerPlan.setTableResumeList(validatorService.getSummaryList());

        var notices = offerPlan.getValidationReport().getNotices();
        var errorNotices = notices.stream().filter(n->n.getSeverity()=="ERROR").findFirst().orElse(new Notice());
        var warningNotices = notices.stream().filter(n->n.getSeverity()=="WARNING").findFirst().orElse(new Notice());
        var infoNotices = notices.stream().filter(n->n.getSeverity()=="INFO").findFirst().orElse(new Notice());

        log.atInfo().log(String.format("********** Erros : %d    Warnings : %d     INFOS : %d", errorNotices.getTotalNotices(), warningNotices.getTotalNotices() , infoNotices.getTotalNotices()));

        //
        //  Save plan to MongoDb
        //  with added status and reports
        //
        mongoService.savePlan(offerPlan);

        GtfsFeedContainer gtfsFeedContainer = validatorService.getFeedContainer();

        if (gtfsFeedContainer.isParsedSuccessfully() && errorNotices.getTotalNotices() == 0)
        {
            ApplicationState.validationBusy = false;
            ApplicationState.entityPersistenceBusy = true;
            // Persist entities to Postgres
            // Perform database functions like
            // - calculating frequencies
            // Async

            //Watchout !!!
//            persistEntitiesToDatabase(gtfsFeedContainer, offerPlanId);
            ApplicationState.entityPersistenceBusy = true;

            var response = new ValidationResult(true,errorNotices.getTotalNotices(),warningNotices.getTotalNotices(),infoNotices.getTotalNotices(), "Validation Completed");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else
        {
            ApplicationState.validationBusy = false;
            ApplicationState.entityPersistenceBusy = false;
            var response = new ValidationResult(false,errorNotices.getTotalNotices(),warningNotices.getTotalNotices(),infoNotices.getTotalNotices(), "Validation Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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

