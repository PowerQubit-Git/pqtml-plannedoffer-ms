package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pt.tml.plannedoffer.database.MongoDbService;
import pt.tml.plannedoffer.database.PostgresService;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.gtfs.GtfsValidationService;
import pt.tml.plannedoffer.models.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Flogger
@RestController
@CrossOrigin("*")
@RequestMapping("plans")
public class UploadController
{

    @Autowired
    MongoDbService mongoService;
    @Autowired
    PostgresService postgresService;
    @Autowired
    GtfsValidationService service;


    @SuppressWarnings("unchecked")
    @PostMapping("upload")
    public ResponseEntity<PlannedOfferInfo> uploadFile(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("user") String publisher,
                                                        HttpServletRequest request)
    {
        if (ApplicationState.uploadBusy || ApplicationState.validationBusy || ApplicationState.entityPersistenceBusy)
        {
            log.atWarning().log(String.format("Upload request refused in busy state u:%s v:%s p:%s",
                    ApplicationState.uploadBusy,
                    ApplicationState.validationBusy,
                    ApplicationState.entityPersistenceBusy));

            return (ResponseEntity<PlannedOfferInfo>) ResponseEntity.status(HttpStatus.CONFLICT);
        }

        ApplicationState.uploadBusy = true;

        PlannedOfferUpload upload = new PlannedOfferUpload();

        try
        {
            long startTime = System.currentTimeMillis();

            Date date = new Date();
            upload.setUploadDate(date);
            upload.setOriginIp(request.getRemoteAddr());
            upload.setPublisherName(publisher);
            upload.setFileSize(file.getSize());
            upload.setFileName(file.getOriginalFilename());
            upload.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

            long endTime = System.currentTimeMillis();
            upload.setLoadTime(endTime - startTime);

            // Save new feed to mongo
            var savedUpload = mongoService.savePlan(upload);

            ApplicationState.uploadBusy = false;
            return ResponseEntity.status(HttpStatus.OK).body(savedUpload.getInfo());
        }
        catch (Exception e)
        {
            log.atWarning().withCause(e).log("Error processing upload.");
        }

        // TODO: detail returned error type
        ApplicationState.uploadBusy = false;
        return (ResponseEntity<PlannedOfferInfo>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping
    HttpEntity<List<PlannedOfferUpload>> getUploads()
    {
        return new HttpEntity<>(mongoService.getMetaPlan());
    }


    @GetMapping("download/{feedId}")
    HttpEntity<byte[]> getOfferBlob(@PathVariable(value = "feedId") String feedId) throws Exception
    {
        PlannedOfferUpload m = mongoService.getPlan(feedId);
        Binary file = m.getFile();
        byte[] documentBody = file.getData();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_RELATED);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + feedId + ".zip");
        header.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, header);
    }


    @DeleteMapping("{feedId}")
    ResponseMessage deleteOffer(@PathVariable(value = "feedId") String feedId) throws Exception
    {
        try
        {
            mongoService.deletePlan(feedId);
            postgresService.deletePlan(feedId);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessage("deleted");
    }


    @GetMapping("validation-report/{feedId}")
    HttpEntity<Notices> getValidationsReport(@PathVariable(value = "feedId") String feedId) throws Exception
    {
        PlannedOfferUpload m = mongoService.getPlan(feedId);
        return new HttpEntity<>(m.getValidationReport());
    }


    @GetMapping("validation-errors/{feedId}")
    HttpEntity<Notices> getErrorsReport(@PathVariable(value = "feedId") String feedId) throws Exception
    {
        PlannedOfferUpload m = mongoService.getPlan(feedId);
        return new HttpEntity<>(m.getErrorsReport());
    }


    @GetMapping("explorer/{feedId}")
    HttpEntity<List<ReportSummary>> getFilesResume(@PathVariable(value = "feedId") String feedId) throws Exception
    {
        PlannedOfferUpload m = mongoService.getPlan(feedId);
        return new HttpEntity<>(m.getTableResumeList());
    }

}
