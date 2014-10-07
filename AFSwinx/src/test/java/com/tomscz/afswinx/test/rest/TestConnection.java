package com.tomscz.afswinx.test.rest;

import static org.junit.Assert.*;

import java.net.ConnectException;

import org.junit.Test;

import com.tomscz.afswinx.rest.connection.ModelConnector;
import com.tomscz.afswinx.rest.dto.AFRestDataPackage;

public class TestConnection {

    @Test
    public void testMetamodelConnection() {
        ModelConnector mc = new ModelConnector("localhost","/AFServer/rest/Person",8080);
        try {
            AFRestDataPackage metamodel = mc.getContent();
            if(metamodel == null || metamodel.getClassInfo() == null){
                fail();
            }
        } catch (ConnectException e) {
           fail(e.getMessage());
        }
    }

}
