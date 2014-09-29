package com.tomscz.afi.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;

public class FileUtils {

    public static File createTemporaryFile(String pathToFile, String type, ServletContext se) {
        InputStream inputStream = se.getResourceAsStream(pathToFile);
        File file = null;
        try {
            file =
                    File.createTempFile(
                            pathToFile.substring(0, pathToFile.length() - type.length()), type);
            FileOutputStream out = new FileOutputStream(file);
            IOUtils.copy(inputStream, out);
            file.deleteOnExit();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }
    
}
