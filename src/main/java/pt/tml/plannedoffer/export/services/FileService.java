package pt.tml.plannedoffer.export.services;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class FileService
{

    /**
     * Create a Directory if not present
     * Keep or clean existing files
     *
     * @param targetDir         The Path of the new or existing directory
     * @param keepExistingFiles Do not erase existing files in existing directory
     * @throws IOException
     */
    public void ensureDirectoryCreation(Path targetDir, boolean keepExistingFiles) throws IOException
    {
        File directory = new File(targetDir.toString());
        if (!directory.exists())
        {
            directory.mkdirs();
        }
        else if (!keepExistingFiles)
        {
            FileUtils.cleanDirectory(directory);
        }
    }
}
