package com.tomscz.afrest.rest.dto;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.marshal.MetaModelBuilder;

public class Transformation {

    
    @Test
    public void testTransformation(){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("examples/xml/NonPrimitiveObject.xml").getFile());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbf.newDocumentBuilder();
            Document document = builder.parse(file);
            MetaModelBuilder formBuilder = new MetaModelBuilder(document);
            formBuilder.buildModel();   
        } catch (ParserConfigurationException | SAXException | IOException | MetamodelException e) {
            fail("Test transformation failed");
        }
    }
}
