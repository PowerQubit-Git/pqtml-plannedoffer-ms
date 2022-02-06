package pt.tml.plannedoffer.export.services;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.tml.plannedoffer.controllers.CsvExportResult;
import pt.tml.plannedoffer.export.annotations.CsvFileName;
import pt.tml.plannedoffer.export.writer.CsvJpaWriter;
import pt.tml.plannedoffer.global.ApplicationState;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Export all  *  @CsvFileNameExport all annotated entities to csv files using the opencsv writer
 */

@Service
@Flogger
public class JpaCsvExportService
{
    @Autowired
    CsvJpaWriter csvJpaWriter;

    @Autowired
    FileService fileService;

    @Autowired
    ZipService zipService;

    @Autowired
    ExportableEntityLoader exportableEntityLoader;

    @PersistenceContext
    EntityManager entityManager;

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
    public CsvExportResult export(String feedId, Path csvFilesFolder, Path zipFilePath) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException
    {
        CsvExportResult exportResult = new CsvExportResult();

        int exported = 0;
        ApplicationState.exportBusy = true;

        // Make sure export folder exists
        fileService.ensureDirectoryCreation(csvFilesFolder, false);

        // Find all exportable Entities (having @CsvFileName annotation)
        var entities = exportableEntityLoader.findTargetEntities();

        for (var entity : entities)
        {
            var entityName = entity.getBindableJavaType().getName();
            var entityType = entity.getJavaType();
            var csvFileName = entityType.getAnnotation(CsvFileName.class).value();
            var csvFilePath = csvFilesFolder.resolve(csvFileName);

            var queryString = String.format("SELECT a FROM %s a WHERE feed_id='%s'", entityName, feedId);
            var query = this.entityManager.createQuery(queryString, entityType);
            var s = query.toString();
            var dataSet = query.getResultList();

            if (dataSet.size() == 0)
            {
                log.atWarning().log("Skipping " + csvFileName + " export. No entries found");
                continue;
            }

            log.atInfo().log("Writing " + dataSet.size() + " entries to " + csvFilePath);

            csvJpaWriter.Write(entityType, dataSet, csvFilePath);

            exported++;
        }

        fileService.ensureDirectoryCreation(zipFilePath, false);
        var zipFileName = String.format("offer-plan-%s.zip", feedId);
        zipService.ZipTxtFilesInDirectory(csvFilesFolder, zipFilePath.resolve(zipFileName));
        ApplicationState.exportBusy = false;

        return exportResult;
    }
}


