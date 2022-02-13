package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.database.MongoDbService;
import pt.tml.plannedoffer.database.PostgresServiceProxy;
import pt.tml.plannedoffer.entities.key.CsvRowFeedIdCompositeKey;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.gtfs.GtfsValidationService;
import pt.tml.plannedoffer.models.PlannedOfferUpload;
import pt.tml.plannedoffer.models.Notice;
import pt.tml.plannedoffer.models.ValidationResult;
import pt.tml.plannedoffer.repository.AgencyRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;


@Flogger
@RestController
@CrossOrigin("*")
@RequestMapping("plans")
public class ValidationController
{
    public static final String VALIDATION_COMPLETED = "Validation Completed";
    public static final String VALIDATION_FAILED = "Validation Failed";
    public static final String SERVICE_BUSY = "Service Busy";
    public static final String GIVEN_FEED_ID_IS_NULL = "given feed id is null";
    public static final String ERROR_VALIDATING_FEED = "error validating feed id : %s";
    public static final String ERROR = "ERROR";
    public static final String WARNING = "WARNING";
    public static final String INFO = "INFO";
    public static final String FAILED_ENTITY_PERSISTENCE = "Failed persisting some or all entities to database";
    public static final String VALIDATION_REQUEST_REFUSED_IN_BUSY_STATE = "Validation request refused in busy state ";
    public static final String ERROR_FEED_ALREADY_VALIDATED = "Feed was previously submitted - feed id : %s";
    public static final String FEED_WITH_ID_S_NOT_FOUND = "feed with id : %s not found";
    public static final String ERROR_OBTAINING_FEED_WITH_ID_S = "error obtaining feed with id : %s";

    @Autowired
    MongoDbService mongoService;
    @Autowired
    PostgresServiceProxy postgresServiceProxy;
    @Autowired
    GtfsValidationService validatorService;
    @Autowired
    AgencyRepository agencyRepository;
    @Value("${entities.persist:true}")
    Boolean persistEntities;


    @PostMapping("validate")
    public ResponseEntity<ValidationResult> uploadFile(@RequestParam("feedId") String offerPlanId, @RequestParam("user") String user, HttpServletRequest request) throws Exception
    {
        PlannedOfferUpload offerPlan;

        if (ApplicationState.uploadBusy || ApplicationState.validationBusy || ApplicationState.entityPersistenceBusy)
        {
            log.atWarning().log(String.format(VALIDATION_REQUEST_REFUSED_IN_BUSY_STATE + "u:%s v:%s p:%s",
                    ApplicationState.uploadBusy,
                    ApplicationState.validationBusy,
                    ApplicationState.entityPersistenceBusy));

            throw new ResponseStatusException(HttpStatus.CONFLICT, SERVICE_BUSY);
        }

        ApplicationState.validationBusy = true;

        //Get zip from Mongo
        try
        {
            offerPlan = mongoService.getPlan(offerPlanId);
            if (offerPlanId == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GIVEN_FEED_ID_IS_NULL);
            }
            // check file content present
            if (offerPlan.getFile() == null)
            {
                String errorString = String.format(FEED_WITH_ID_S_NOT_FOUND, offerPlanId);
                log.atWarning().log(errorString);
                ApplicationState.validationBusy = false;

                throw new ResponseStatusException(HttpStatus.NO_CONTENT, errorString);
            }
        }
        catch (Exception e)
        {
            String errorString = String.format(ERROR_OBTAINING_FEED_WITH_ID_S, offerPlanId);
            log.atSevere().log(errorString);
            ApplicationState.validationBusy = false;
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorString, e);
        }

        // Validate zip files in plan
        try
        {
            validatorService.validatePlan(offerPlan);
        }
        catch (Exception e)
        {
            ApplicationState.validationBusy = false;
            String errorString = String.format(ERROR_VALIDATING_FEED, offerPlanId);
            log.atSevere().log(errorString);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorString, e);
        }

        // Validation process completed. Update plan with results
        ApplicationState.validationBusy = false;
        offerPlan.setValidationReport(validatorService.getValidationsReport());
        offerPlan.setErrorsReport(validatorService.getErrorsReport());
        offerPlan.setTableResumeList(validatorService.getSummaryList());

        // Gather validation results
        var notices = offerPlan.getValidationReport().getNotices();
        var errorNotices = notices.stream().filter(n -> Objects.equals(n.getSeverity(), ERROR)).findFirst().orElse(new Notice());
        var warningNotices = notices.stream().filter(n -> Objects.equals(n.getSeverity(), WARNING)).findFirst().orElse(new Notice());
        var infoNotices = notices.stream().filter(n -> Objects.equals(n.getSeverity(), INFO)).findFirst().orElse(new Notice());
        GtfsFeedContainer gtfsFeedContainer = validatorService.getFeedContainer();

        log.atInfo().log(String.format("Validation Results: parsed: %s Errors: %d  Warnings: %d  Infos: %d", gtfsFeedContainer.isParsedSuccessfully(), errorNotices.getTotalNotices(), warningNotices.getTotalNotices(), infoNotices.getTotalNotices()));

        // Assert validation success
        boolean validationSucceeded = gtfsFeedContainer.isParsedSuccessfully() && errorNotices.getTotalNotices() == 0;

        //  Save plan to Mongo with added status and reports
        offerPlan.setPublisherName(user);
        offerPlan.setValidated(validationSucceeded);
        offerPlan.setValidationDate(new Date());
        mongoService.savePlan(offerPlan);

        if (validationSucceeded)
        {
            // Validation passed with no errors. Persist entities to database.
            if (persistEntities)
            {
                var agencyOptional = agencyRepository.findById(new CsvRowFeedIdCompositeKey(offerPlanId, 2));
                // Abort if same feed already in database (checking in the 2nd row of the agency table)
                if (agencyOptional.isPresent())
                {
                    ApplicationState.entityPersistenceBusy = false;
                    String errorString = String.format(ERROR_FEED_ALREADY_VALIDATED, offerPlanId);
                    log.atSevere().log(errorString);
                    throw new ResponseStatusException(HttpStatus.CONFLICT, errorString);
                }
                persistEntitiesToDatabase(gtfsFeedContainer, offerPlanId);
            }
            var response = new ValidationResult(true, errorNotices.getTotalNotices(), warningNotices.getTotalNotices(), infoNotices.getTotalNotices(), VALIDATION_COMPLETED);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else
        {
            var response = new ValidationResult(false, errorNotices.getTotalNotices(), warningNotices.getTotalNotices(), infoNotices.getTotalNotices(), VALIDATION_FAILED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



    /**
     * Persist entities to Postgres
     * Also run business transformation procedures
     * like computing frequencies
     */
    @Async
    @LogExecutionTime(started = "Persisting to DB", completed = "DB persist completed")
    public void persistEntitiesToDatabase(GtfsFeedContainer gtfsFeedContainer, String offerPlanId)
    {
        try
        {
            ApplicationState.entityPersistenceBusy = true;
            postgresServiceProxy.persistAllEntities(offerPlanId, gtfsFeedContainer);
        }
        catch (Exception e)
        {
            ApplicationState.entityPersistenceBusy = false;
            log.atSevere().withCause(e).log(FAILED_ENTITY_PERSISTENCE);
        }
    }
}

