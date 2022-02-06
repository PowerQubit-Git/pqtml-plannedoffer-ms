package pt.tml.plannedoffer.models;

import java.util.List;

public class Notices
{
    private List<Notice> notices;

    public Notices()
    {
    }

    public Notices(List<Notice> notices)
    {
        this.notices = notices;
    }

    public List<Notice> getNotices()
    {
        return notices;
    }

    public void setNotices(List<Notice> notices)
    {
        this.notices = notices;
    }
}
