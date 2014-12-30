package com.tomscz.afrest.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;

/**
 * This is util class which can work with files.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class FileUtils {

    /**
     * This method will create temporal file.
     * @param pathToFile path to current file
     * @param type of file
     * @param servletContext actual servlet context
     * @return This will return temporal file.
     * @throws IOException if file could be created then this exception is thrown.
     */
    public static File createTemporaryFile(String pathToFile, String type, ServletContext servletContext)
            throws IOException {
        InputStream inputStream = servletContext.getResourceAsStream(pathToFile);
        File file =
                File.createTempFile(pathToFile.substring(0, pathToFile.length() - type.length()),
                        type);
        FileOutputStream out = new FileOutputStream(file);
        IOUtils.copy(inputStream, out);
        file.deleteOnExit();
        return file;
    }
    
}
