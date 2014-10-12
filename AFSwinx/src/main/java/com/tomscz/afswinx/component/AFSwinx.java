package com.tomscz.afswinx.component;

import java.util.HashMap;

import javax.swing.JPanel;

import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.form.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

/**
 * This class is facade to using AFSwinx. Use getInstance to get unique instance in your
 * application.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinx {

    private static AFSwinx instance;

    private HashMap<String, AFSwinxTopLevelComponent> components;

    private AFSwinx() {
        components = new HashMap<>();
    }

    public static synchronized AFSwinx getInstance() {
        if (instance == null) {
            instance = new AFSwinx();
        }
        return instance;
    }

    /**
     * This method build {@link AFSwinxForm} add them to existed component and retrieve it. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * 
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param modelConnection connection to end point where model is defined couldn't be null
     * @param dataConnection connection to end point where data are, if null then component will be
     *        empty
     * @param postConnection connection to end point where will be send update or insert request if
     *        null no request is send
     * @return it returns AFRorm which could be used as standard {@link JPanel} component
     */
    public AFSwinxForm buildForm(String componentKeyName, AFSwinxConnection modelConnection,
            AFSwinxConnection dataConnection, AFSwinxConnection postConnection) {
        AFSwinxForm form = new AFSwinxForm(modelConnection, dataConnection, postConnection);
        form.buildComponent();
        addComponent(form, componentKeyName);
        return form;
    }

    public AFSwinxTopLevelComponent buildTable() {
        // TODO finish it
        return null;
    }

    private void addComponent(AFSwinxTopLevelComponent componentToAdd, String key) {
        components.put(key, componentToAdd);
    }

    public AFSwinxTopLevelComponent getExistedComponent(String componentName) {
        return components.get(componentName);
    }
}
