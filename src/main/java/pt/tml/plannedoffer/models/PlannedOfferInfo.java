package pt.tml.plannedoffer.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Document(collection = "PlannedOfferUploads")
public class PlannedOfferInfo
{

    @Transient
    public static final String SEQUENCE_NAME = "raw_input_sequence";
    public String originIp;
    @Id
    private String id;
    private String publisherName;
    private String validatorName;
    private String fileName;

    private Date uploadDate;
    private Date validationDate;

    private boolean validated;

    private long loadTime;
    private long fileSize;
}
