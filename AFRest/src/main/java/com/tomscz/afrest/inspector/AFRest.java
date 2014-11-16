package com.tomscz.afrest.inspector;

import javax.servlet.ServletContext;

import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass,ServletContext servletContext) throws AFRestException;
    public AFDataPack generateDataObject(Class<?> clazz,Object objectToGenerate) throws IllegalArgumentException;

}
