package pt.tml.plannedoffer.models;

import lombok.Data;

@Data
public class ReportSummary
{
    private String tableName;
    private String tableStatus;

    public ReportSummary()
    {
    }

    public ReportSummary(String tableName, String tableStatus)
    {
        this.tableName = tableName;
        this.tableStatus = tableStatus;
    }
}
