package pt.tml.plannedoffer.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SpTripsByLine
{
    private int total;

    @Id
    private String line_id;

    public SpTripsByLine(int total, String line_id)
    {
        this.total = total;
        this.line_id = line_id;
    }

    public SpTripsByLine()
    {
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public String getLine_id()
    {
        return line_id;
    }

    public void setLine_id(String line_id)
    {
        this.line_id = line_id;
    }
}