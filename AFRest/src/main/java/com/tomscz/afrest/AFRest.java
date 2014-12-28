package com.tomscz.afrest;

import java.util.HashMap;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass) throws MetamodelException;
    
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig) throws MetamodelException;
    
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig, String mainLayout) throws MetamodelException;
    
    public AFMetaModelPack generateSkeleton(String fullClassName, HashMap<String, String> structureConfig, String mainLayout) throws MetamodelException;
  
    public void setMainLayout(String layout);
    
    public void setMapping(String mapping);
    
}
