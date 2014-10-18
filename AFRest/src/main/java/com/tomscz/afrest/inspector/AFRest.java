package com.tomscz.afrest.inspector;

import javax.servlet.ServletContext;

import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afrest.ws.mappers.MapperType;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass, MapperType mapper, ServletContext servletContext) throws AFRestException;
    public AFDataPack generateDataObject(Class<?> clazz,Object objectToGenerate) throws IllegalArgumentException;

}
