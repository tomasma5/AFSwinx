package com.tomscz.afswinx.marshal;

import com.tomscz.afswinx.exception.MetamodelException;
import com.tomscz.afswinx.rest.dto.AFRestDataPackage;

public interface ModelBuilder {

    public AFRestDataPackage buildModel() throws MetamodelException;
    
}
