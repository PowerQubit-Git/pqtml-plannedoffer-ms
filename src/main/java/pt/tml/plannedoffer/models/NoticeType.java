package pt.tml.plannedoffer.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeType
{
    private String childFilename;
    private String childFieldName;
    private String parentFilename;
    private String parentFieldName;
    private String filename;
    private String fieldName;
    private String csvRowNumber;
    private String fieldValue;

    public NoticeType()
    {
    }

    public NoticeType(String filename, String fieldName, String csvRowNumber, String fieldValue)
    {
        this.filename = filename;
        this.fieldName = fieldName;
        this.csvRowNumber = csvRowNumber;
        this.fieldValue = fieldValue;
    }

}
