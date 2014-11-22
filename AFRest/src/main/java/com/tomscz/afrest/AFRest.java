package com.tomscz.afrest;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass) throws MetamodelException;
    public AFDataPack generateDataObject(Class<?> clazz,Object objectToGenerate) throws IllegalArgumentException;

}
