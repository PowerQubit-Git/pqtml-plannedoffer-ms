package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.export.services.JpaCsvExportService;
import pt.tml.plannedoffer.export.services.PgCopyCsvExportService;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.models.ResponseMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@CrossOrigin("*")
@Flogger
public class ExportCsvController
{
    @Autowired
    JpaCsvExportService jpaCsvExportService;

    @Autowired
    PgCopyCsvExportService pgCopyCsvExportService;

    @Value("${generator.csv.directory}")
    private String csvFilesFolder;
    @Value("${generator.csv.zip-file}")
    private String zipFileFolder;
    @Value("${generator.usePostgresCopy}")
    private boolean usePostgresCopy;


    /**
     * Export all Entities with @CsvFileName and @CsvBindByNameOrder annotations
     *
     */
    @GetMapping("csv-generate/{feedId}")
    @LogExecutionTime
    public HttpEntity ExportCsv(@PathVariable String feedId) throws Exception
    {
        CsvExportResult exportResult = new CsvExportResult();

        if (ApplicationState.exportBusy /* ||  ApplicationState.entityPersistenceBusy*/)
        {
            log.atWarning().log(String.format("Csv generation refused in busy state u:%s v:%s p:%s e:%s", ApplicationState.uploadBusy, ApplicationState.validationBusy, ApplicationState.entityPersistenceBusy, ApplicationState.exportBusy));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("service busy"));
        }

        try
        {
            if (usePostgresCopy)
            {
                exportResult = pgCopyCsvExportService.export(feedId, Path.of(csvFilesFolder), Path.of(zipFileFolder));
            }
            else
            {
                exportResult = jpaCsvExportService.export(feedId, Path.of(csvFilesFolder), Path.of(zipFileFolder));
            }

            if (exportResult.getExportedRows() == 0)
            {
                String nothingExported = "Nothing found to export";
                log.atWarning().log(nothingExported);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage(nothingExported));
            }

        }
        catch (Exception e)
        {
            String errorString = "Unable to export csv files";
            log.atSevere().withCause(e).log(errorString);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(errorString));
        }

        // String responseString = String.format("Zip file created successfully. Exported %d rows in %d files", exported;
        return ResponseEntity.status(HttpStatus.OK).body(exportResult);
    }


    @GetMapping("zip-download/{feedId}")
    ResponseEntity<Resource> DownloadZip(@PathVariable String feedId, HttpServletRequest request) throws Exception
    {
        Path filePath = Path.of(zipFileFolder, String.format("offer-plan-%s.zip", feedId));
        var resource = new UrlResource(filePath.toUri());
        if (!resource.exists())
        {
            String errorString = String.format("No zip available for id : %s", feedId);
            log.atSevere().log(errorString);
            return ResponseEntity.notFound().build();
        }

        String contentType = null;
        try
        {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (IOException ex)
        {
            //logger.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null)
        {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

    }
}

