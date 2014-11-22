package com.tomscz.afrest.rest.dto;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.marshal.FormBuilder;
import com.tomscz.afrest.marshal.ModelBuilder;
import com.tomscz.afrest.marshal.ModelFactory;

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
            FormBuilder formBuilder = new FormBuilder(document);
            formBuilder.buildModel();   
        } catch (ParserConfigurationException | SAXException | IOException | MetamodelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
}
