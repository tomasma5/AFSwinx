package com.tomscz.afswinx.component.form;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.tomscz.afrest.layout.Layout;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.ComponentBuilder;
import com.tomscz.afswinx.component.builders.FieldBuilder;
import com.tomscz.afswinx.component.builders.abstraction.BaseLayoutBuilder;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionPack;
import com.tomscz.afswinx.rest.connection.ConnectionParser;

/**
 * This is form builder. This class is responsible for create {@link AFSwinxForm} component.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxFormBuilder implements ComponentBuilder {

    private String componentKeyName;

    private ResourceBundle localization;

    private HashMap<String, String> connectionParameters;
    private AFSwinxConnection modelConnection;
    private AFSwinxConnection dataConnection;
    private AFSwinxConnection postConnection;
    private String connectionKey;
    private File connectionConfiguration;

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
     * @return it returns this builder which could be used to build {@link AFSwinxForm}
     */
    public AFSwinxFormBuilder initBuilder(String componentKeyName,
            AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
        this.componentKeyName = componentKeyName;
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
        return this;
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
     * @return it returns this builder which could be used to build {@link AFSwinxForm}
     */
    public AFSwinxFormBuilder initBuilder(String componentKeyName, File connectionConfiguration,
            String connectionKey, String connectionValue) {
        this.componentKeyName = componentKeyName;
        this.connectionConfiguration = connectionConfiguration;
        this.connectionKey = connectionKey;
        this.connectionParameters = new HashMap<String, String>();
        connectionParameters.put("value", connectionValue);
        return this;
    }

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @return it returns this builder which could be used to build {@link AFSwinxForm}
     */
    public AFSwinxFormBuilder initBuilder(String componentKeyName, File connectionConfiguration,
            String connectionKey) {
        this.componentKeyName = componentKeyName;
        this.connectionConfiguration = connectionConfiguration;
        this.connectionKey = connectionKey;
        return this;
    }

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @param connectionParameters which will be added to connection configuration file based on EL.
     * @return it returns this builder which could be used to build {@link AFSwinxForm}
     */
    public AFSwinxFormBuilder initBuilder(String componentKeyName, File connectionConfiguration,
            String connectionKey, HashMap<String, String> connectionParameters) {
        this.componentKeyName = componentKeyName;
        this.connectionConfiguration = connectionConfiguration;
        this.connectionKey = connectionKey;
        this.connectionParameters = connectionParameters;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AFSwinxFormBuilder setLocalization(ResourceBundle localization) {
        this.localization = localization;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AFSwinxForm buildComponent() throws AFSwinxBuildException {
        AFSwinxForm form;
        //Obtaion model connection
        if (modelConnection != null) {
            form = new AFSwinxForm(modelConnection, dataConnection, postConnection);
        } else if (connectionKey != null && connectionConfiguration != null) {
            ConnectionParser connectionParser =
                    new ConnectionParser(connectionKey, connectionParameters);
            AFSwinxConnectionPack connections =
                    connectionParser.parseDocument(Utils
                            .buildDocumentFromFile(connectionConfiguration));
            form =
                    new AFSwinxForm(connections.getMetamodelConnection(),
                            connections.getDataConnection(), connections.getPostConnection());
        } else {
            //Model connection is important if it could be found then throw exception
            throw new AFSwinxBuildException(
                    "There is error during building AFSwinxForm. Connection was not specified. Did you used initBuilder method before build?");
        }
        try {
            //Build component
            this.buildComponent(form);
            //Obtain data
            AFDataPack data = form.getData();
            form.fillData(data);
            AFSwinx.getInstance().addComponent(form, componentKeyName);
        } catch (AFSwinxConnectionException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }
        return form;
    }

    /**
     * This method will build form based on metamodel
     * @param form which will be build
     * @throws AFSwinxConnectionException if exception during retrieve metamodel ocurre
     */
    private void buildComponent(AFSwinxForm form) throws AFSwinxConnectionException {
        AFMetaModelPack metaModelPack = form.getModel();
        AFClassInfo classInfo = metaModelPack.getClassInfo();
        if (classInfo != null) {
            // Convert TopLevelLayout to layout
            Layout layout = null;
            if (classInfo.getLayout() != null) {
                layout = new Layout();
                layout.setLayoutDefinition(classInfo.getLayout().getLayoutDefinition());
                layout.setLayoutOrientation(classInfo.getLayout().getLayoutOrientation());
            }
            // Initialize layout builder
            BaseLayoutBuilder layoutBuilder = new BaseLayoutBuilder(layout);
            for (String fieldId : classInfo.getFieldInfo().keySet()) {
                AFFieldInfo fieldInfo = classInfo.getFieldInfo().get(fieldId);
                FieldBuilder builder =
                        WidgetBuilderFactory.getInstance().createWidgetBuilder(fieldInfo);
                if (localization == null) {
                    localization = AFSwinx.getInstance().getLocalization();
                }
                builder.setLocalization(localization);
                this.addComponent(builder.buildComponent(fieldInfo), layoutBuilder, form);
            }
            // Build layout
            layoutBuilder.buildLayout(form);
        }
    }

    /**
     * This method add component which will be created to panel and layout builder
     * @param panelToAdd new panel which will be add
     * @param layoutBuilder builder which holds all panels in this form
     * @param form current form
     */
    public void addComponent(AFSwinxPanel panelToAdd, BaseLayoutBuilder layoutBuilder,
            AFSwinxForm form) {
        form.getPanels().put(panelToAdd.getPanelId(), panelToAdd);
        form.add(panelToAdd);
        panelToAdd.setAfParent(form);
        layoutBuilder.addComponent(panelToAdd);
    }

}
