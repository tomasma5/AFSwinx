package com.tomscz.afswinx.component.builders;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.tomscz.afswinx.component.ComponentBuilder;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

public abstract class BaseComponentBuilder<T> implements ComponentBuilder<T> {

    protected String componentKeyName;

    protected ResourceBundle localization;

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
    public T initBuilder(String componentKeyName, File connectionConfiguration,
            String connectionKey) {
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
    public T initBuilder(String componentKeyName,
            AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
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

    
}
