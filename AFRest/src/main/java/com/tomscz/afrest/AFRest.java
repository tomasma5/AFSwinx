package com.tomscz.afrest;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass) throws MetamodelException;
  
}
