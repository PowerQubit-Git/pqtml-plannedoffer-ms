package pt.tml.plannedoffer.export.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pt.tml.plannedoffer.aspects.LogExecutionTime;
import pt.tml.plannedoffer.export.strategies.HeaderColumnNameAndOrderMappingStrategy;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

@Flogger
@Component
public class CsvJpaWriter
{

    @Value("${generator.csv.encoding}")
    private String encoding;

    // required
    public CsvJpaWriter()
    {
    }

    @LogExecutionTime
    public void Write(Class<?> entityType, List<?> dataSet, Path csvFilePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException
    {
        var writer = new FileWriter(csvFilePath.toString(), Charset.forName(encoding));
        var mappingStrategy = new HeaderColumnNameAndOrderMappingStrategy<>(entityType);
        var sbc = new StatefulBeanToCsvBuilder(writer).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withApplyQuotesToAll(false).withOrderedResults(true).withQuotechar('"').withMappingStrategy(mappingStrategy).build();
        sbc.write(dataSet.listIterator());
        writer.close();
    }

}
