package com.tomscz.afswinx.component.abstraction;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.component.builders.ComponentDataPacker;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.connection.AFConnector;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.RestBuilderFactory;

/**
 * This is abstract top level component. This components are used to hold another concrete
 * components. It component can be added to {@link JPanel} as well.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class AFSwinxTopLevelComponent extends JPanel implements AFSwinxInteraction, ComponentReserialization {

    private HashMap<String, ComponentDataPacker> panels = new HashMap<String, ComponentDataPacker>();
    
    protected AFSwinxConnection modelConnection;
    protected AFSwinxConnection postConnection;
    protected AFSwinxConnection dataConnection;
    
    private static final long serialVersionUID = 1L;

    public abstract SupportedComponents getComponentType();

    public ResourceBundle localization;

    @Override
    public AFMetaModelPack getModel() throws AFSwinxConnectionException {
        try {
            AFConnector<AFMetaModelPack> modelConnector =
                    new AFConnector<AFMetaModelPack>(getModelConnection(), AFMetaModelPack.class);
            return modelConnector.getContent();
        } catch (ConnectException e) {
            throw new AFSwinxConnectionException(e.getLocalizedMessage());
        }
    }

    @Override
    public Object getData() throws AFSwinxConnectionException {
        if(getDataConnection() == null){
            return new AFDataPack("");
        }
        try {
            AFConnector<JsonElement> dataConnector =
                    new AFConnector<JsonElement>(getDataConnection(), JsonElement.class);
            return dataConnector.getContent();
        } catch (ConnectException e) {
            throw new AFSwinxConnectionException(e.getLocalizedMessage());
        }
    }
    
    @Override
    public Object generatePostData() {
        // before building data and sending, validate actual data
        boolean isValid = validateData();
        if(!isValid){
            return null;
        }
        BaseRestBuilder dataBuilder = RestBuilderFactory.getInstance().getBuilder(getPostConnection());
        Object data = dataBuilder.reselialize(this.resealize());
        return data;
    }
    
    @Override
    public void postData() throws AFSwinxConnectionException {
        if (getPostConnection() == null) {
            throw new IllegalStateException(
                    "The post connection was not specify. Check your XML configuration or Connection which was used to build this form");
        }
        Object data = generatePostData();
        if(data == null){
            return;
        }
        AFConnector<Object> dataConnector =
                new AFConnector<Object>(getPostConnection(), Object.class);
        try {
            dataConnector.doPost(data.toString());
        } catch (ConnectException e) {
            throw new AFSwinxConnectionException(e.getMessage());
        }
    }

    @Override
    public void changeLocalization(ResourceBundle localization) {
        // TODO Auto-generated method stub
        
    }
    
    public HashMap<String, ComponentDataPacker> getPanels() {
        return panels;
    }
    
    public AFSwinxConnection getModelConnection() {
        return modelConnection;
    }
    
    public AFSwinxConnection getPostConnection() {
        return postConnection;
    }

    public AFSwinxConnection getDataConnection() {
        return dataConnection;
    }
    
    
    
}
