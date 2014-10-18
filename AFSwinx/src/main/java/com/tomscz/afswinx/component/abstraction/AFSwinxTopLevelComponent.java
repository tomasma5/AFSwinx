package com.tomscz.afswinx.component.abstraction;

import java.net.ConnectException;

import javax.swing.JPanel;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.rest.connection.AFConnector;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

public abstract class AFSwinxTopLevelComponent extends JPanel implements AFSwinxInteraction {

    private static final long serialVersionUID = 1L;
    
    protected abstract SupportedComponents getComponentType();
    protected abstract AFSwinxConnection getModelConnection();
    protected abstract AFSwinxConnection getPostConnection();
    protected abstract AFSwinxConnection getDataConnection();
    
    @Override
    public AFMetaModelPack getModel() throws ConnectException{
        AFConnector<AFMetaModelPack> modelConnector = new AFConnector<AFMetaModelPack>(getModelConnection(),AFMetaModelPack.class);
        return modelConnector.getContent();  
    }
    
    @Override
    public AFDataPack getData() throws ConnectException{
            AFConnector<AFDataPack> dataConnector = new AFConnector<AFDataPack>(getDataConnection(), AFDataPack.class);
            return dataConnector.getContent();
    }

}
