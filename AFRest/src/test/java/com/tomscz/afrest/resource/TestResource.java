package com.tomscz.afrest.resource;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class TestResource {

    @Test
    public void test() {
        loadResources();
    }
    
    public void loadResources(){
        URI uri;
        File f = null;
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("af/layout.config.xml");
            File createdFile = File.createTempFile("layout.config", "xml");
            createdFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(createdFile)) {
                IOUtils.copy(in, out);
            }
            uri = this.getClass().getClassLoader().getResource("af/layout.config.xml").toURI();
            String name = uri.getPath();
            System.out.println(name);
            f = new File(uri);
        } catch (URISyntaxException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(new FileInputStream(f));
            System.out.print(document.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail();
        }
    }

}
