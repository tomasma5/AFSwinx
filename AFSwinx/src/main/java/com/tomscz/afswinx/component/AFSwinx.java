package com.tomscz.afswinx.component;

import java.util.HashMap;

import com.tomscz.afswinx.component.abstraction.AFSwinxComponent;
import com.tomscz.afswinx.component.form.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

public class AFSwinx {

    private static AFSwinx instance;

    private HashMap<String, AFSwinxComponent> components;

    private AFSwinx() {
        components = new HashMap<>();
    }

    public static synchronized AFSwinx getInstance() {
        if (instance == null) {
            instance = new AFSwinx();
        }
        return instance;
    }

    public AFSwinxComponent getComponent(String key) {
        return components.get(key);
    }

    public AFSwinxForm buildForm(AFSwinxConnection modelConnection,
            AFSwinxConnection dataConnection, AFSwinxConnection postConnection) {
        AFSwinxForm form = new AFSwinxForm(modelConnection, dataConnection, postConnection);
        
        return null;
    }

    public AFSwinxComponent buildTable() {
        //TODO finish it
        return null;
    }
}
