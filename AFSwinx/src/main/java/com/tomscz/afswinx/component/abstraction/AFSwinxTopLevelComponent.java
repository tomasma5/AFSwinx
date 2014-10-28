package com.tomscz.afswinx.component.abstraction;

import java.net.ConnectException;
import java.util.Properties;

import javax.swing.JPanel;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.rest.connection.AFConnector;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public abstract class AFSwinxTopLevelComponent extends JPanel implements AFSwinxInteraction {

    private static final long serialVersionUID = 1L;

    protected abstract SupportedComponents getComponentType();

    protected abstract AFSwinxConnection getModelConnection();

    protected abstract AFSwinxConnection getPostConnection();

    protected abstract AFSwinxConnection getDataConnection();

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
    public AFDataPack getData() throws AFSwinxConnectionException {
        try {
            AFConnector<AFDataPack> dataConnector =
                    new AFConnector<AFDataPack>(getDataConnection(), AFDataPack.class);
            return dataConnector.getContent();
        } catch (ConnectException e) {
            throw new AFSwinxConnectionException(e.getLocalizedMessage());
        }
    }
    
    @Override
    public void makeLocalization(Properties localization) throws AFSwinxConnectionException{
        throw new UnsupportedOperationException("This operation is not supported yet");
    }

}
