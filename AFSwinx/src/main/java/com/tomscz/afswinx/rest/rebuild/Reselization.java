package com.tomscz.afswinx.rest.rebuild;

import java.util.List;

import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

/**
 * This interface specify method, which are used to rebuild data and these data will be send back to
 * server or this data comes from server and must be interpreted. This interface provide only method
 * which leads to data. No connection or request is performed there.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface Reselization {

    /**
     * This method build data which will be set to server. Data are build from componentData
     * parameter in this method.
     * 
     * @param componentData data which will be used to build data which will be send on server.
     * @return Data which will be send on server.
     */
    public Object reselialize(AFDataHolder componentData);

    /**
     * This method serialize data from server to data which are used to fill component.
     * 
     * @param componentData data from server
     * @return Data object which can be used to set data to components.
     */
    public List<AFDataPack> serialize(Object componentData);

}
