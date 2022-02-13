package pt.tml.plannedoffer.export.services;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.strategies.CsvBindByNameOrder;
import pt.tml.plannedoffer.export.writer.CsvPgCopyWriter;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.models.CsvExportResult;

import javax.persistence.Table;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;


/**
 * Export all @CsvFileName annotated entities to csv files using direct postgres \copy command
 */

@Service
@Flogger
public class PgCopyCsvExportService
{
    @Autowired
    CsvPgCopyWriter csvPgCopyWriter;

    @Autowired
    ExportableEntityLoader exportableEntityLoader;

    @Autowired
    FileService fileService;

    @Autowired
    ZipService zipService;

    /**
     * Export all annotated entities to csv files using direct postgres \copy command
     *
     * @param feedId         Id of the feed to export
     * @param csvFilesFolder Destination folder for the exported csv files
     * @param zipFilePath    Full path of the output zip file
     * @throws CsvRequiredFieldEmptyException
     * @throws CsvDataTypeMismatchException
     * @throws IOException
     * @throws InterruptedException
     */
    public CsvExportResult export(String feedId, String agencyId, Path csvFilesFolder, Path zipFilePath) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException, URISyntaxException
    {

        int exportedFIles = 0;
        long exportedRows = 0;

        CsvExportResult exportResult = new CsvExportResult();

        ApplicationState.exportBusy = true;

        // Make sure export folder exists
        fileService.ensureDirectoryCreation(csvFilesFolder, false);

        // Find all exportable Entities (having @CsvFileName annotation)
        var entities = exportableEntityLoader.findTargetEntities();

        for (var entity : entities)
        {
            var entityType = entity.getJavaType();
            var csvFileName = entityType.getAnnotation(CsvFileName.class).value();
            var tableName = entityType.getAnnotation(Table.class).name();
            var fieldList = entityType.getAnnotation(CsvBindByNameOrder.class).value();
            var csvFilePath = csvFilesFolder.resolve(csvFileName);

            // translate tableName to views in order to convert table to view queries
            tableName = "vw_agencyfeed_" + tableName;


            // Export the csv file using postgres \copy command
            var tableExportedRows = csvPgCopyWriter.Write(tableName, List.of(fieldList), feedId, agencyId, csvFilePath);
            if (tableExportedRows > 0)
            {
                exportedFIles++;
                exportedRows += tableExportedRows;

                exportResult.getExportedFiles().add(csvFileName);
                exportResult.setExportedRows(exportResult.getExportedRows() + tableExportedRows);
            }
        }

        fileService.ensureDirectoryCreation(zipFilePath, false);
        var zipFileName = String.format("offer-plan-%s-%s.zip", feedId, agencyId);
        zipService.ZipTxtFilesInDirectory(csvFilesFolder, zipFilePath.resolve(zipFileName));
        ApplicationState.exportBusy = false;

        exportResult.setGeneratedZipName(zipFileName);

        return exportResult;

    }
}


