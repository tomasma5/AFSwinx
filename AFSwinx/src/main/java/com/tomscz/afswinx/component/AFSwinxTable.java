package com.tomscz.afswinx.component;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class AFSwinxTable extends AFSwinxTopLevelComponent {

    @Override
    public void fillData(AFDataPack dataPack) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean validateData() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public AFDataHolder resealize() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SupportedComponents getComponentType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AFSwinxConnection getModelConnection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AFSwinxConnection getPostConnection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AFSwinxConnection getDataConnection() {
        // TODO Auto-generated method stub
        return null;
    }

}
