package pt.tml.plannedoffer.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SpRowsByTable
{
    @Id
    @Column(name = "gtfs_table")
    private String gtfsTable;
    private int counter;

    public SpRowsByTable(String gtfs_table, int counter)
    {
        this.gtfsTable = gtfs_table;
        this.counter = counter;
    }

    public SpRowsByTable()
    {
    }

    public String getGtfsTable()
    {
        return gtfsTable;
    }

    public void setGtfsTable(String gtfsTable)
    {
        this.gtfsTable = gtfsTable;
    }

    public int getCounter()
    {
        return counter;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }
}