package com.tomscz.afswinx.test.rest;

import static org.junit.Assert.*;

import java.net.ConnectException;

import org.junit.Test;

import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.connection.AFConnector;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

public class TestConnection {

    @Test
    public void testMetamodelConnection() {
        System.out.println("testMedamodelConnection started");
        AFSwinxConnection connection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFConnector<AFMetaModelPack> mc =
                new AFConnector<AFMetaModelPack>(connection, AFMetaModelPack.class);
        try {
            AFMetaModelPack metamodel = mc.doRequest(null);
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
            AFDataPack data = dc.doRequest(null);
            if (data == null || data.getData() == null || data.getData().isEmpty()) {
                fail();
            }
        } catch (ConnectException e) {
            fail(e.getMessage());
        }
        System.out.println("testDataConnection passed");
    }

    @Test
    public void testPostConnection() {
        System.out.println("testPostConnection started");
        AFSwinxConnection connection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFConnector<Object> dcPost = new AFConnector<Object>(connection, Object.class);
        String simplePerson = "{\"firstName\":\"Martin\",\"lastName\":\"Novy\"}";
        try {
            dcPost.doRequest(simplePerson);
            if (dcPost.getStatusCode() != 200) {
                fail("Test post connection failed.");
            }
        } catch (ConnectException e) {
            fail(e.getMessage());
        }
        System.out.println("testPostConnection passed");
    }

}
