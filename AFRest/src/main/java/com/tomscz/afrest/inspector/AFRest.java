package com.tomscz.afrest.inspector;

import javax.servlet.ServletContext;

import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.ws.mappers.MapperType;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.dto.data.AFDataPack;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass, MapperType mapper, ServletContext servletContext) throws AFRestException;
    public AFDataPack generateDataObject(Class<?> clazz,Object objectToGenerate) throws IllegalArgumentException;

}
