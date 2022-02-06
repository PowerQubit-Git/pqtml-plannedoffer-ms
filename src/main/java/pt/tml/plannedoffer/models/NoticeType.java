package pt.tml.plannedoffer.models;

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

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getCsvRowNumber()
    {
        return csvRowNumber;
    }

    public void setCsvRowNumber(String csvRowNumber)
    {
        this.csvRowNumber = csvRowNumber;
    }

    public String getFieldValue()
    {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue)
    {
        this.fieldValue = fieldValue;
    }

    public String getChildFilename()
    {
        return childFilename;
    }

    public void setChildFilename(String childFilename)
    {
        this.childFilename = childFilename;
    }

    public String getChildFieldName()
    {
        return childFieldName;
    }

    public void setChildFieldName(String childFieldName)
    {
        this.childFieldName = childFieldName;
    }

    public String getParentFilename()
    {
        return parentFilename;
    }

    public void setParentFilename(String parentFilename)
    {
        this.parentFilename = parentFilename;
    }

    public String getParentFieldName()
    {
        return parentFieldName;
    }

    public void setParentFieldName(String parentFieldName)
    {
        this.parentFieldName = parentFieldName;
    }
}
