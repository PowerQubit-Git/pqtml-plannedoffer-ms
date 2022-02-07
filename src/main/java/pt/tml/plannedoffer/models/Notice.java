package pt.tml.plannedoffer.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

}
