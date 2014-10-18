package com.tomscz.afrest.marshal;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

public interface ModelBuilder {

    public AFMetaModelPack buildModel() throws MetamodelException;
    
}
