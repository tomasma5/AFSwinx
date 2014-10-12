package com.tomscz.afswinx.component.abstraction;

import java.net.ConnectException;

import javax.swing.JPanel;

import com.tomscz.afswinx.common.SupportedComponents;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.ModelConnector;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;

public abstract class AFSwinxTopLevelComponent extends JPanel implements AFSwinxInteraction {

    private static final long serialVersionUID = 1L;
    
    protected abstract SupportedComponents getComponentType();
    protected abstract AFSwinxConnection getModelConnection();
    protected abstract AFSwinxConnection getPostConnection();
    protected abstract AFSwinxConnection getDataConnection();
    
    @Override
    public AFMetaModelPack getModel() throws ConnectException{
        ModelConnector modelConnector = new ModelConnector(getModelConnection());
        return modelConnector.getContent();  
    }
    
    @Override
    public void fillData() throws ConnectException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postData() throws ConnectException{
        // TODO Auto-generated method stub
        
    }

}
