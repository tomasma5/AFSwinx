package com.tomscz.afswinx.component.abstraction;

import java.util.Properties;

import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public interface AFSwinxInteraction {

    public AFMetaModelPack getModel() throws AFSwinxConnectionException;
    public AFDataPack getData() throws AFSwinxConnectionException;
    public void fillData() throws AFSwinxConnectionException;
    public void postData() throws AFSwinxConnectionException;
    public void buildComponent() throws AFSwinxConnectionException;
    public void makeLocalization(Properties localization) throws AFSwinxConnectionException;

}
