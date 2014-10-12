package com.tomscz.afi.inspector;

import javax.servlet.ServletContext;

import com.tomscz.afi.exceptions.AFRestException;
import com.tomscz.afi.ws.mappers.MapperType;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.dto.data.AFDataPack;

public interface AFRest {
    
    public AFMetaModelPack generateSkeleton(String entityClass, MapperType mapper, ServletContext servletContext) throws AFRestException;
    public AFDataPack generateDataObject(Class<?> clazz,Object objectToGenerate) throws IllegalArgumentException;

}
