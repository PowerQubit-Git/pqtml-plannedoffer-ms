package pt.tml.plannedoffer.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "PlannedOfferUploads")
public class PlannedOfferUpload extends PlannedOfferInfo
{

    private Notices validationReport;
    private Notices errorsReport;
    private List<ReportSummary> tableResumeList;
    private Binary file;

    public PlannedOfferInfo getInfo()
    {
        var res = new PlannedOfferInfo();
        res.setId(getId());
        res.setPublisherName(getPublisherName());
        res.setOriginIp(getOriginIp());
        res.setUploadDate(getUploadDate());
        res.setLoadTime(getLoadTime());
        res.setFileSize(getFileSize());
        res.setFileName(getFileName());
        return res;
    }
}
