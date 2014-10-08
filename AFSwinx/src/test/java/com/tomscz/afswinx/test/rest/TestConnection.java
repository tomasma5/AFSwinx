package com.tomscz.afswinx.test.rest;

import static org.junit.Assert.*;

import java.net.ConnectException;

import org.junit.Test;

import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.ModelConnector;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;

public class TestConnection {

    @Test
    public void testMetamodelConnection() {
        AFSwinxConnection connection = new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        ModelConnector mc = new ModelConnector(connection);
        try {
            AFMetaModelPack metamodel = mc.getContent();
            if(metamodel == null || metamodel.getClassInfo() == null){
                fail();
            }
        } catch (ConnectException e) {
           fail(e.getMessage());
        }
    }

}
