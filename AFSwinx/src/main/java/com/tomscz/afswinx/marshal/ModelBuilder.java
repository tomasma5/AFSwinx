package com.tomscz.afswinx.marshal;

import com.tomscz.afswinx.exception.MetamodelException;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;

public interface ModelBuilder {

    public AFMetaModelPack buildModel() throws MetamodelException;
    
}
