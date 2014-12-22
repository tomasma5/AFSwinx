package com.tomscz.afswinx.component.builders;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

/**
 * This interface specify operation which must provide every builder which is used to build
 * {@link AFSwinxTopLevelComponent}. The generic class T should be the same as is class of builder
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface ComponentBuilder<T> {

    /**
     * This method set localization to builder. The generic is used because you have to return any
     * type of builder.
     * 
     * @param localization resource bundle with localization
     * @return actual builder class which will be used to another builder settings or to return
     *         object which was build
     */
    public T setLocalization(ResourceBundle localization);

    /**
     * This method set localization to builder. The generic is used because you have to return any
     * type of builder.
     * 
     * @param localization resource bundle with localization
     * @return actual builder class which will be used to another builder settings or to return
     *         object which was build
     */
    public T setSkin(Skin skin);
    
    /**
     * This method build concrete component. The generic is used because you can build various
     * components.
     * 
     * @return component which will be build based on initial and current settings
     * @throws AFSwinxBuildException
     */
    public AFSwinxTopLevelComponent buildComponent() throws AFSwinxBuildException;

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
     * @return it returns this builder which could be used to build
     */
    public T initBuilder(String componentKeyName, AFSwinxConnection modelConnection,
            AFSwinxConnection dataConnection, AFSwinxConnection postConnection);

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * 
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @param connectionValue value which will be added to connection configuration based on EL.
     * @return it returns this builder which could be used to build
     */
    public T initBuilder(String componentKeyName, File connectionConfiguration,
            String connectionKey, String connectionValue);

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * 
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @return it returns this builder which could be used to build
     */
    public T initBuilder(String componentKeyName, File connectionConfiguration,
            String connectionKey);

    /**
     * This method init builder. It set variable based on which will be obtained connections. There
     * are connection types, which are used to retrieve model definitions, data and post data back.
     * 
     * @param componentKeyName key in which you should retrieve this component back and do other
     *        staff with it
     * @param connectionConfiguration file in which will be found connection
     * @param connectionKey key of connection which will be found in connection configuration file
     * @param connectionParameters which will be added to connection configuration file based on EL.
     * @return it returns this builder which could be used to build
     */
    public T initBuilder(String componentKeyName, File connectionConfiguration,
            String connectionKey, HashMap<String, String> connectionParameters);
}
