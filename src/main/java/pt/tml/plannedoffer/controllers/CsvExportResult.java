package pt.tml.plannedoffer.controllers;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CsvExportResult
{

    private List<String> exportedFiles;
    private long exportedRows;
    private String generatedZipName;
    public CsvExportResult()
    {
        this.exportedFiles = new ArrayList<>();
    }

}
