package pt.tml.plannedoffer.export.writer;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static pt.tml.plannedoffer.export.strategies.HeaderColumnNameAndOrderMappingStrategy.camelToSnake;

@Flogger
@Component
public class CsvPgCopyWriter
{

    @Value("${generator.csv.encoding}")
    private String encoding;

    @Value("${postgres.port}")
    private String postgresPort;

    @Value("${postgres.host}")
    private String postgresHost;

    @Value("${postgres.database}")
    private String postgresDatabase;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${postgres.executable}")
    private String postgresExecutable;


    public int Write(String tableName, List<String> fieldList, String feedId, Path csvFilePath) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, InterruptedException
    {
        int lines = 0;

        log.atInfo().log(String.format("Exporting %s", tableName));

        var listOfFields = fieldList.stream().map(f -> f = camelToSnake(f)).collect(Collectors.joining(","));
        var queryString = String.format("(SELECT %s FROM ms_planned_offer.%s WHERE feed_id='%s')", listOfFields, tableName, feedId);
        var pgCmd = String.format("%s -h %s -p %s -d %s -U %s -c \"", postgresExecutable, postgresHost, postgresPort, postgresDatabase, postgresUsername);
        var pgParams = String.format("\\copy %s TO '%s' CSV DELIMITER ',' HEADER ENCODING '%s'\"", queryString, csvFilePath, encoding);

        var builder = new ProcessBuilder().command("sh", "-c", pgCmd + pgParams);//.inheritIO();

        Process process = builder.start();

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getInputStream()));

        int exitCode = process.waitFor();

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csvFilePath)));
            while (reader.readLine() != null)
                lines++;
            reader.close();
        }
        catch (Exception e)
        {
        }

        log.atInfo().log(String.format("******* Able to count : %d", lines));


        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader2.readLine()) != null)
        {
            stringBuilder.append(line);
            stringBuilder.append(System.getProperty("line.separator"));
        }

        var s = stringBuilder.toString();

        log.atInfo().log(String.format("******* Able to read : %s", s));

        var number = 0;
        Pattern p = Pattern.compile("COPY ([0-9]+)");
        Matcher m = p.matcher(s);
        if (m.find())
        {
            var numberString = m.group(1);
            number = Integer.parseInt(numberString);
        }

        log.atInfo().log(String.format("******* Able to parse : %d", number));

        return lines;
    }
}
