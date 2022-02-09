package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.database.MongoDbService;
import pt.tml.plannedoffer.database.PostgresServiceProxy;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.gtfs.GtfsValidationService;
import pt.tml.plannedoffer.models.Notice;
import pt.tml.plannedoffer.models.PlannedOfferUpload;
import pt.tml.plannedoffer.models.ValidationResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Flogger
@RestController
@CrossOrigin("*")
@RequestMapping("plan")
public class ValidationController
{
    @Autowired
    MongoDbService mongoService;
    @Autowired
    PostgresServiceProxy postgresServiceProxy;
    @Autowired
    GtfsValidationService validatorService;
    @Value("${entities.persist:true}")
    Boolean persistEntities;


    @PostMapping("validate")
    public ResponseEntity<ValidationResult> uploadFile(
            @RequestParam("feedId") String offerPlanId,
            @RequestParam("user") String user, HttpServletRequest request)
    {

//      var remoteIpAddress= request.getHeader("X-FORWARDED-FOR");
//      var remoteIpAddress= request.getRemoteAddress());

        PlannedOfferUpload offerPlan;

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
            validatorService.validatePlan(offerPlan);
        }
        catch (Exception e)
        {
            ApplicationState.validationBusy = false;
            String errorString = String.format("error validating feed id : %s", offerPlanId);
            log.atSevere().log(errorString);
            var response = new ValidationResult();
            response.setMessage(errorString);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


        //
        // Validation process completed
        // update plan with results
        //
        ApplicationState.validationBusy = false;
        offerPlan.setValidationReport(validatorService.getValidationsReport());
        offerPlan.setErrorsReport(validatorService.getErrorsReport());
        offerPlan.setTableResumeList(validatorService.getSummaryList());

        // Gather validation results
        var notices = offerPlan.getValidationReport().getNotices();
        var errorNotices = notices.stream().filter(n -> Objects.equals(n.getSeverity(), "ERROR")).findFirst().orElse(new Notice());
        var warningNotices = notices.stream().filter(n -> Objects.equals(n.getSeverity(), "WARNING")).findFirst().orElse(new Notice());
        var infoNotices = notices.stream().filter(n -> Objects.equals(n.getSeverity(), "INFO")).findFirst().orElse(new Notice());
        GtfsFeedContainer gtfsFeedContainer = validatorService.getFeedContainer();

        // log
        log.atInfo().log(String.format("Validation Results: parsed: %s Errors: %d  Warnings: %d  Infos: %d",
                gtfsFeedContainer.isParsedSuccessfully(),
                errorNotices.getTotalNotices(),
                warningNotices.getTotalNotices(),
                infoNotices.getTotalNotices()));


        // Assert validation success
        boolean validationSucceeded =  gtfsFeedContainer.isParsedSuccessfully() && errorNotices.getTotalNotices() == 0;

        //  Save plan to MongoDb
        //  with added status and reports
        offerPlan.setPublisherName(user);
        offerPlan.setValidated(validationSucceeded);
        offerPlan.setValidationDate(new Date());
        mongoService.savePlan(offerPlan);

        if (validationSucceeded)
        {
            // Validation passed with no errors. Persist entities to database.
            if(persistEntities)
            {
                persistEntitiesToDatabase(gtfsFeedContainer, offerPlanId);
            }

            var response = new ValidationResult(true,
                    errorNotices.getTotalNotices(),
                    warningNotices.getTotalNotices(),
                    infoNotices.getTotalNotices(),
                    "Validation Completed");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else
        {
            var response = new ValidationResult(false,
                    errorNotices.getTotalNotices(),
                    warningNotices.getTotalNotices(),
                    infoNotices.getTotalNotices(),
                    "Validation Failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    /**
     *
     * Persist entities to Postgres
     * Also run database transformation procedures
     * like computing frequencies
     *
     */
    @Async
    @LogExecutionTime(started = "Persisting to DB", completed = "DB persist completed")
    public void persistEntitiesToDatabase(GtfsFeedContainer gtfsFeedContainer, String offerPlanId)
    {
        try
        {
            postgresServiceProxy.persistAllEntities(offerPlanId, gtfsFeedContainer);
        }
        catch (Exception e)
        {
            ApplicationState.entityPersistenceBusy = false;
            log.atSevere().withCause(e).log("Failed persisting some or all entities to database");
        }
    }
}

