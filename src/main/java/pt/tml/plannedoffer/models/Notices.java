package pt.tml.plannedoffer.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

}
