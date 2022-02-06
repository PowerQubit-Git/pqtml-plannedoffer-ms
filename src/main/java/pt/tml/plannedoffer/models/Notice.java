package pt.tml.plannedoffer.models;

import java.util.List;

public class Notice
{
    private String code;
    private String severity;
    private int totalNotices;
    private List<NoticeType> sampleNotices;

    public Notice()
    {
    }

    public Notice(String code, String severity, int totalNotices, List<NoticeType> sampleNotices)
    {
        this.code = code;
        this.severity = severity;
        this.totalNotices = totalNotices;
        this.sampleNotices = sampleNotices;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getSeverity()
    {
        return severity;
    }

    public void setSeverity(String severity)
    {
        this.severity = severity;
    }

    public int getTotalNotices()
    {
        return totalNotices;
    }

    public void setTotalNotices(int totalNotices)
    {
        this.totalNotices = totalNotices;
    }

    public List<NoticeType> getSampleNotices()
    {
        return sampleNotices;
    }

    public void setSampleNotices(List<NoticeType> sampleNotices)
    {
        this.sampleNotices = sampleNotices;
    }
}
