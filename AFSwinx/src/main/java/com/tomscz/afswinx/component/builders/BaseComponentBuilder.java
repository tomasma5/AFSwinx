package com.tomscz.afswinx.component.builders;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.ComponentBuilder;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.skin.BaseSkin;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseLayoutBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionPack;
import com.tomscz.afswinx.rest.connection.ConnectionParser;

public abstract class BaseComponentBuilder<T> implements ComponentBuilder<T> {

    protected String componentKeyName;

    protected ResourceBundle localization;
    protected Skin skin;
    
    protected HashMap<String, String> connectionParameters;
    protected AFSwinxConnection modelConnection;
    protected AFSwinxConnection dataConnection;
    protected AFSwinxConnection postConnection;
    protected String connectionKey;
    protected File connectionConfiguration;

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
    public T initBuilder(String componentKeyName, File connectionConfiguration, String connectionKey) {
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
     * @param postConnection connection to end point where will be send update or insert request if
     *        null no request is send
     * @return it returns this builder which could be used to build {@link AFSwinxTopLevelComponent}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T initBuilder(String componentKeyName, AFSwinxConnection modelConnection,
            AFSwinxConnection dataConnection, AFSwinxConnection postConnection) {
        this.componentKeyName = componentKeyName;
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
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
     * @param connectionValue value which will be added to connection configuration based on EL.
     * @return it returns this builder which could be used to build {@link AFSwinxTopLevelComponent}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T initBuilder(String componentKeyName, File connectionConfiguration,
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
    public T initBuilder(String componentKeyName, File connectionConfiguration,
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
            ConnectionParser connectionParser =
                    new ConnectionParser(connectionKey, connectionParameters);
            AFSwinxConnectionPack connections =
                    connectionParser.parseDocument(Utils
                            .buildDocumentFromFile(connectionConfiguration));
            modelConnection = connections.getMetamodelConnection();
            dataConnection = connections.getDataConnection();
            postConnection = connections.getPostConnection();
        } else {
            // Model connection is important if it could be found then throw exception
            throw new AFSwinxBuildException(
                    "There is error during building AFSwinxForm. Connection was not specified. Did you used initBuilder method before build?");
        }
    }
    
    /**
     * This method set data to form. It build for each widget his field
     * 
     * @param classInfo which will be inspected
     * @param layoutBuilder layout builder which will be used
     * @param form which is builded
     * @param key of current field. It is used to determine which class belongs to which fields
     */
    protected void buildFields(AFClassInfo classInfo, BaseLayoutBuilder layoutBuilder,
            AFSwinxTopLevelComponent form, String key) {
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
                if (localization == null) {
                    localization = AFSwinx.getInstance().getLocalization();
                }
                if(skin == null){
                    skin = AFSwinx.getInstance().getApplicationSkin();
                    if(skin == null){
                        skin = new BaseSkin();
                    }
                }
                builder.setLocalization(localization);
                builder.setSkin(skin);
                // Use generated key
                String uniquieKey = Utils.generateKey(key, fieldInfo.getId());
                fieldInfo.setId(uniquieKey);
                AFSwinxPanel panelToAdd = builder.buildComponent(fieldInfo);
                this.addComponent(panelToAdd, layoutBuilder, form);
            }
        }
    }
    
    /**
     * This method add component which will be created to panel and layout builder
     * 
     * @param panelToAdd new panel which will be add
     * @param layoutBuilder builder which holds all panels in this form
     * @param form current form
     */
    protected abstract void addComponent(AFSwinxPanel panelToAdd, BaseLayoutBuilder layoutBuilder,
            AFSwinxTopLevelComponent component);

}
