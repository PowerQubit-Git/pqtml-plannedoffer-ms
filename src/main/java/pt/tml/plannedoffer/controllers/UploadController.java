package pt.tml.plannedoffer.controllers;

import lombok.extern.flogger.Flogger;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.tml.plannedoffer.database.MongoDbService;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.models.PlannedOfferInfo;
import pt.tml.plannedoffer.models.PlannedOfferUpload;
import pt.tml.plannedoffer.models.Notices;
import pt.tml.plannedoffer.models.ReportSummary;
import pt.tml.plannedoffer.gtfs.GtfsValidationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Flogger
@RestController
@CrossOrigin("*")
public class UploadController
{

    @Autowired
    MongoDbService mongoService;

    @Autowired
    GtfsValidationService service;

    @SuppressWarnings("unchecked")
    @PostMapping("upload")
    public ResponseEntity<PlannedOfferInfo> uploadFile(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("user") String publisher,
                                                       HttpServletRequest request)
    {

//        var remoteIpAddress= request.getHeader("X-FORWARDED-FOR");
//        var remoteIpAddress= request.getRemoteAddress());

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


    @GetMapping("uploads")
    List<PlannedOfferUpload> getUploads()
    {
        return mongoService.getMetaPlan();
    }


    @GetMapping("download/{feedId}")
    HttpEntity<byte[]> getOfferBlob(@PathVariable String feedId) throws Exception
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


    @GetMapping("validation-report/{feedId}")
    Notices getValidationsReport(@PathVariable String feedId) throws Exception
    {
        PlannedOfferUpload m = mongoService.getPlan(feedId);
        return m.getValidationReport();
    }


    @GetMapping("validation-errors/{feedId}")
    Notices getErrorsReport(@PathVariable String feedId) throws Exception
    {
        PlannedOfferUpload m = mongoService.getPlan(feedId);
        return m.getErrorsReport();
    }


    @GetMapping("explorer/{feedId}")
    List<ReportSummary> getFilesResume(@PathVariable String feedId) throws Exception
    {
        PlannedOfferUpload m = mongoService.getPlan(feedId);
        return m.getTableResumeList();
    }


}
