package com.tomscz.afrest;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass) throws MetamodelException;
    
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig) throws MetamodelException;
    
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig, String mainLayout) throws MetamodelException;
  
}
