package com.tomscz.afswinx.test.rest;

import static org.junit.Assert.*;

import java.net.ConnectException;

import org.junit.Test;

import com.tomscz.afswinx.rest.connection.AFConnector;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.dto.data.AFDataPack;

public class TestConnection {

    @Test
    public void testMetamodelConnection() {
        System.out.println("testMedamodelConnection started");
        AFSwinxConnection connection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFConnector<AFMetaModelPack> mc =
                new AFConnector<AFMetaModelPack>(connection, AFMetaModelPack.class);
        try {
            AFMetaModelPack metamodel = mc.getContent();
            if (metamodel == null || metamodel.getClassInfo() == null) {
                fail();
            }
        } catch (ConnectException e) {
            fail(e.getMessage());
        }
        System.out.println("testMedamodelConnection passed");
    }

    @Test
    public void testDataConnection() { 
        System.out.println("testMedamodelConnection started");
        AFSwinxConnection connection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person/1");
        AFConnector<AFDataPack> dc = new AFConnector<AFDataPack>(connection, AFDataPack.class);
        try {
            AFDataPack data = dc.getContent();
            if (data == null || data.getData() == null || data.getData().isEmpty()) {
                fail();
            }
        } catch (ConnectException e) {
            fail(e.getMessage());
        }
        System.out.println("testDataConnection passed");
    }

}
