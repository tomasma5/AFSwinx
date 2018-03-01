package com.tomscz.afswinx.component.builders;

import java.util.HashMap;
import java.util.ResourceBundle;

import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.skin.BaseSkin;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseLayoutBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionPack;
import com.tomscz.afswinx.rest.connection.JsonConnectionParser;
import com.tomscz.afswinx.validation.RetypeValidator;
import org.json.JSONObject;

public abstract class BaseComponentBuilder<T> implements ComponentBuilder<T> {

    protected ResourceBundle localization;
    protected Skin skin;
    
    protected String componentKeyName;
    protected HashMap<String, String> connectionParameters;
    protected AFSwinxConnection modelConnection;
    protected AFSwinxConnection dataConnection;
    protected AFSwinxConnection sendConnection;
    protected String connectionKey;
    protected JSONObject connectionConfiguration;

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * 
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @return it returns this builder which could be used to build {@link AFSwinxTopLevelComponent}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T initBuilder(String componentKeyName, JSONObject connectionConfiguration, String connectionKey) {
        this.componentKeyName = componentKeyName;
        this.connectionConfiguration = connectionConfiguration;
        this.connectionKey = connectionKey;
        return (T) this;
    }

    /**
     * This method init builder. It set existed connection to builder. There are connection types,
     * which are used to retrieve model definitions, data and post data back.
     *
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param modelConnection connection to end point where model is defined couldn't be null
     * @param dataConnection connection to end point where data are, if null then component will be
     *        empty
     * @param sendConnection connection to end point where will be send update or insert request if
     *        null no request is send. It also could be used HTTP method as put, send and delete.
     * @return it returns this builder which could be used to build {@link AFSwinxTopLevelComponent}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T initBuilder(String componentKeyName, AFSwinxConnection modelConnection,
            AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        this.componentKeyName = componentKeyName;
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.sendConnection = sendConnection;
        return (T) this;
    }


    @SuppressWarnings("unchecked")
    @Override
    public T initBuilder(JSONObject connectionConfiguration) {
        this.connectionConfiguration = connectionConfiguration;
        return (T) this;
    }

    public void setConnectionKey(String connectionKey) {
        this.connectionKey = connectionKey;
    }

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * 
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @param connectionValue value which will be added to connection configuration based on EL.
     * @return it returns this builder which could be used to build {@link AFSwinxTopLevelComponent}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T initBuilder(String componentKeyName, JSONObject connectionConfiguration,
            String connectionKey, String connectionValue) {
        this.componentKeyName = componentKeyName;
        this.connectionConfiguration = connectionConfiguration;
        this.connectionKey = connectionKey;
        this.connectionParameters = new HashMap<String, String>();
        connectionParameters.put("value", connectionValue);
        return (T) this;
    }

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * 
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @param connectionParameters which will be added to connection configuration file based on EL.
     * @return it returns this builder which could be used to build {@link AFSwinxTopLevelComponent}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T initBuilder(String componentKeyName, JSONObject connectionConfiguration,
            String connectionKey, HashMap<String, String> connectionParameters) {
        this.componentKeyName = componentKeyName;
        this.connectionConfiguration = connectionConfiguration;
        this.connectionKey = connectionKey;
        this.connectionParameters = connectionParameters;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T setLocalization(ResourceBundle localization) {
        this.localization = localization;
        return (T) this;
    }

    protected void initializeConnections() throws AFSwinxBuildException {
        if (modelConnection == null && connectionKey != null && connectionConfiguration != null) {
            JsonConnectionParser connectionParser =
                    new JsonConnectionParser(connectionKey, connectionParameters);
            AFSwinxConnectionPack connections =
                    connectionParser.parse(connectionConfiguration);
            modelConnection = connections.getMetamodelConnection();
            dataConnection = connections.getDataConnection();
            sendConnection = connections.getSendConnection();
        } else {
            // Model connection is important if it could be found then throw exception
            throw new AFSwinxBuildException(
                    "There is error during building AFSwinxForm. Connection was not specified. Did you used initBuilder method before build?");
        }
    }
    
    /**
     * This method set data to widget. It build for each widget his field.
     * 
     * @param classInfo which will be inspected
     * @param layoutBuilder layout builder which will be used
     * @param form which is builded
     * @param key of current field. It is used to determine which class belongs to which fields
     */
    protected void buildFields(AFClassInfo classInfo, BaseLayoutBuilder layoutBuilder,
            AFSwinxTopLevelComponent form, String key) throws AFSwinxBuildException {
        if (localization == null) {
            localization = AFSwinx.getInstance().getLocalization();
        }
        if(skin == null){
            skin = AFSwinx.getInstance().getApplicationSkin();
            if(skin == null){
                skin = new BaseSkin();
            }
        }
        form.localization = localization;
        // For each field
        for (AFFieldInfo fieldInfo : classInfo.getFieldInfo()) {
            // If its class then inspect it recursively
            if (fieldInfo.getClassType()) {
                for (AFClassInfo classInfoChildren : classInfo.getInnerClasses()) {
                    // There could be more inner class choose the right one
                    if (classInfoChildren.getName() != null
                            && classInfoChildren.getName().equals(fieldInfo.getId())) {
                        // Recursively call this method with new key, which will specify unique link
                        // on parent
                        buildFields(classInfoChildren, layoutBuilder, form,
                                Utils.generateKey(key, fieldInfo.getId()));
                    }
                }
            } else {
                // Build field
                WidgetBuilder builder =
                        WidgetBuilderFactory.getInstance().createWidgetBuilder(fieldInfo);
                builder.setLocalization(localization);
                builder.setSkin(skin);
                // Use generated key
                String uniqueKey = Utils.generateKey(key, fieldInfo.getId());
                fieldInfo.setId(uniqueKey);
                AFSwinxPanel panelToAdd = builder.buildComponent(fieldInfo);
                panelToAdd.setVisible(fieldInfo.getVisible());
                this.addComponent(panelToAdd, layoutBuilder, form);
                // If panel should be retype, then create validator and add it
                if(panelToAdd.isRetype()){
                    //Retyped panel. This will be clone
                    AFSwinxPanel retypePanel = builder.buildComponent(fieldInfo);
                    retypePanel.setVisible(fieldInfo.getVisible());
                    //Generate clone id
                    retypePanel.setPanelId(Utils.generateCloneKey(retypePanel.getPanelId()));
                    RetypeValidator retype = new RetypeValidator(panelToAdd);
                    retype.setLocalization(localization);
                    retypePanel.addValidator(retype);
                    RetypeValidator panelRetype = new RetypeValidator(retypePanel);
                    panelRetype.setLocalization(localization);
                    panelToAdd.addValidator(panelRetype);
                    this.addComponent(retypePanel, layoutBuilder, form);
                }
               
            }
        }
    }
    
    /**
     * This method add component which will be created to panel and layout builder
     * 
     * @param panelToAdd new panel which will be add
     * @param layoutBuilder builder which holds all panels in this component
     * @param component current component
     */
    protected abstract void addComponent(AFSwinxPanel panelToAdd, BaseLayoutBuilder layoutBuilder,
            AFSwinxTopLevelComponent component);

}
