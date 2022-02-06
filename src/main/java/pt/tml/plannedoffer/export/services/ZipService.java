package pt.tml.plannedoffer.export.services;

import com.google.common.io.Files;
import org.springframework.stereotype.Component;
import pt.tml.plannedoffer.aspects.LogExecutionTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipService
{
    /**
     * Zip all files in the given list to the parameter zipFilePath
     *
     * @param srcFiles
     * @param zipFilePath Full path for the output zip file
     * @throws IOException
     */
    public static void zipFiles(List<String> srcFiles, Path zipFilePath) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(String.valueOf(zipFilePath));
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles)
        {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0)
            {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }

    /**
     * Zip all .txt files in the target folder
     *
     * @param sourceFolder The source directory for files to be zipped
     * @param zipFilePath  The destination zip file Path
     * @throws IOException
     */
    @LogExecutionTime(started = "Zipping files")
    public void ZipTxtFilesInDirectory(Path sourceFolder, Path zipFilePath) throws IOException
    {
        var fileList = new ArrayList<>(ListDirFilesOfType(sourceFolder, "txt"));

        for (int i = 0; i < fileList.size(); i++)
        {
            Path path = sourceFolder.resolve(fileList.get(i));
            fileList.set(i, String.valueOf(path));
        }
        zipFiles(fileList, zipFilePath);
    }

    /**
     * Get all files in directory with parameter passed file extension
     *
     * @param directory     The target directory
     * @param fileExtension The filter file extension as String
     * @return List with file names
     */
    public List<String> ListDirFilesOfType(Path directory, String fileExtension)
    {
        return Stream.of(Objects.requireNonNull(new File(directory.toString()).listFiles())).filter(file -> !file.isDirectory() && Files.getFileExtension(file.getName()).equals(fileExtension)).map(File::getName).collect(Collectors.toList());
    }

}


