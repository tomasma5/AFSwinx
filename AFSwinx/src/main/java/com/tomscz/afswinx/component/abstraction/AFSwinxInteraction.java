package com.tomscz.afswinx.component.abstraction;

import java.net.ConnectException;

import com.tomscz.afswinx.rest.dto.AFMetaModelPack;

public interface AFSwinxInteraction {

    public AFMetaModelPack getModel() throws ConnectException;
    public void fillData() throws ConnectException;
    public void postData() throws ConnectException;
    public void buildComponent();

}
