package com.tomscz.afswinx.component.abstraction;

import java.util.List;

import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

/**
 * This interface specify operation which should implement all afswinx components which contains
 * another components. These components are known as {@link AFSwinxTopLevelComponent}. The reason is
 * that components should provide user getting and posting data.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface AFSwinxInteraction {

    /**
     * This method getModel from server. This model is used to create concrete component.
     * 
     * @return Model which is used to create view components.
     * @throws AFSwinxConnectionException if exception during obtaining model occur.
     */
    public AFMetaModelPack getModel() throws AFSwinxConnectionException;

    /**
     * This method get concrete data from server to model. This data are data which will be set to
     * concrete component.
     * 
     * @return data which will be set to components, it could be some of {@link com.tomscz.afswinx.rest.connection.HeaderType} based on
     *         connection
     * @throws AFSwinxConnectionException if exception during obtaining data to model occur.
     */
    public Object getData() throws AFSwinxConnectionException;

    /**
     * This method set data to model
     * 
     */
    public void fillData(List<AFDataPack> dataPack);

    public void sendData() throws AFSwinxConnectionException;

    /**
     * This method validate all fields and if there is no valid field then return false. If there
     * are no valid fields then message is show up
     * 
     * @return true if data in fields are valid, false otherwise
     */
    public boolean validateData();

    /**
     * This method generate data which will be post. It do validations and create data object which
     * will be posted. But post is not performed.
     * 
     * @return send data object
     */
    public Object generateSendData();

    /**
     * This method clear data in component.
     */
    public void clearData();

}
