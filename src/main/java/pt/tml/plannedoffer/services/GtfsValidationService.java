package pt.tml.plannedoffer.services;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.flogger.Flogger;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.powerqubit.validator.core.input.CountryCode;
import pt.powerqubit.validator.core.input.CurrentDateTime;
import pt.powerqubit.validator.core.input.GtfsInput;
import pt.powerqubit.validator.core.input.GtfsZipFileInput;
import pt.powerqubit.validator.core.notice.IOError;
import pt.powerqubit.validator.core.notice.NoticeContainer;
import pt.powerqubit.validator.core.notice.URISyntaxError;
import pt.powerqubit.validator.core.table.GtfsFeedContainer;
import pt.powerqubit.validator.core.table.GtfsFeedLoader;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.powerqubit.validator.core.validator.DefaultValidatorProvider;
import pt.powerqubit.validator.core.validator.ValidationContext;
import pt.powerqubit.validator.core.validator.ValidatorLoader;
import pt.powerqubit.validator.core.validator.ValidatorLoaderException;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.database.MongoDbService;
import pt.tml.plannedoffer.database.PostgresService;
import pt.tml.plannedoffer.models.PlannedOfferUpload;
import pt.tml.plannedoffer.models.Notices;
import pt.tml.plannedoffer.models.ReportSummary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// TODO: Check with LN

@Component
@Flogger
public class GtfsValidationService
{

    private static final int numberOfThreads = 1;

    @Autowired
    PostgresService pgService;

    @Autowired
    MongoDbService mongoService;


    @Getter
    private GtfsFeedContainer feedContainer;
    @Getter
    private Notices validationsReport;
    @Getter
    private Notices errorsReport;
    @Getter
    private List<ReportSummary> summaryList;


    private GtfsFeedLoader feedLoader;
    private ValidatorLoader validatorLoader;
    private GtfsInput gtfsInput;
    private NoticeContainer noticeContainer;

    /**
     * Close the gtfs zip file
     */
    public static void closeZipFile(GtfsInput gtfsInput, NoticeContainer noticeContainer)
    {
        try
        {
            gtfsInput.close();
        }
        catch (IOException e)
        {
            log.atSevere().withCause(e).log("Cannot close GTFS input - Zip File");
            noticeContainer.addSystemError(new IOError(e));
        }
    }

    @LogExecutionTime(started = "Validating GTFS files  ---------------------------------------")
    public GtfsFeedContainer validatePlan(PlannedOfferUpload plan) throws ValidatorLoaderException, ClassNotFoundException
    {

        //
        // Load validator classes
        //
        try
        {
            validatorLoader = new ValidatorLoader();
        }
        catch (ValidatorLoaderException e)
        {
            log.atSevere().withCause(e).log("Cannot load validator classes");
            throw (e);
        }

        //
        // Load table loader classes
        //
        try
        {
            feedLoader = new GtfsFeedLoader();
            feedLoader.setNumThreads(numberOfThreads);
        }
        catch (Exception e)
        {
            log.atSevere().withCause(e).log("Cannot create feed loader");
            throw (e);
        }

        noticeContainer = new NoticeContainer();

        //
        //  Set validation input content
        //
        try
        {
            byte[] file = plan.getFile().getData();
            gtfsInput = createGtfsInput(file);
        }
        catch (IOException e)
        {
            String err1 = "Cannot load GTFS feed";
            log.atSevere().withCause(e).log(err1);
            noticeContainer.addSystemError(new IOError(e));
        }
        catch (URISyntaxException e)
        {
            String err2 = "Syntax error in URI";
            log.atSevere().withCause(e).log(err2);
            noticeContainer.addSystemError(new URISyntaxError(e));
        }

        //
        // Build Validation Context
        //
        ValidationContext validationContext = ValidationContext.builder().
                setCountryCode(CountryCode.forStringOrUnknown(CountryCode.ZZ)).
                setCurrentDateTime(new CurrentDateTime(ZonedDateTime.now(ZoneId.systemDefault())))
                .build();

        //
        // Perform validation ann return feedContainer
        //
        try
        {
            feedContainer = loadAndValidate(validatorLoader, feedLoader, noticeContainer, gtfsInput, validationContext);
            exportReport(plan, noticeContainer, feedContainer);
            closeZipFile(gtfsInput, noticeContainer);
            return feedContainer;
        }
        catch (InterruptedException e)
        {
            String err3 = "Validation was interrupted";
            log.atSevere().withCause(e).log(err3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generates and exports reports for both validation notices and system errors reports.
     */
    public void exportReport(PlannedOfferUpload upload, final NoticeContainer noticeContainer, GtfsFeedContainer feedContainer)
    {
        Gson gson = new Gson();
        validationsReport = gson.fromJson(noticeContainer.exportValidationNotices(), Notices.class);
        errorsReport = gson.fromJson(noticeContainer.exportSystemErrors(), Notices.class);

        Map<String, GtfsTableContainer<?>> map = feedContainer.getTables();
        Iterator<Map.Entry<String, GtfsTableContainer<?>>> it = map.entrySet().iterator();
        summaryList = new ArrayList<>();
        while (it.hasNext())
        {
            Map.Entry<String, GtfsTableContainer<?>> pair = it.next();
            String x = pair.getKey() + " - " + pair.getValue().getTableStatus();
            summaryList.add(new ReportSummary(pair.getKey(), pair.getValue().getTableStatus().toString()));
        }
    }


    public GtfsInput createGtfsInput(byte[] file) throws IOException, URISyntaxException
    {
        return new GtfsZipFileInput(new ZipFile(new SeekableInMemoryByteChannel(file)));
    }


    public GtfsFeedContainer loadAndValidate(ValidatorLoader validatorLoader, GtfsFeedLoader feedLoader, NoticeContainer noticeContainer, GtfsInput gtfsInput, ValidationContext validationContext) throws InterruptedException
    {
        GtfsFeedContainer feedContainer;
        feedContainer = feedLoader.loadAndValidate(gtfsInput, new DefaultValidatorProvider(validationContext, validatorLoader), noticeContainer);
        return feedContainer;
    }

    public void printSummary(long startNanos, GtfsFeedContainer feedContainer)
    {
        final long endNanos = System.nanoTime();
        if (!feedContainer.isParsedSuccessfully())
        {
            System.out.println(" ----------------------------------------- ");
            System.out.println("|       !!!    PARSING FAILED    !!!      |");
            System.out.println("|   Most validators were never invoked.   |");
            System.out.println("|   Please see report.json for details.   |");
            System.out.println(" ----------------------------------------- ");
        }
        double t = (endNanos - startNanos) / 1e9;
        System.out.printf("Validation took %.3f seconds%n", t);
        System.out.println(feedContainer.tableTotals());
    }
}
