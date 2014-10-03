package com.tomscz.afi.inspector;

import javax.servlet.ServletContext;

import com.tomscz.afi.exceptions.SkeletonException;
import com.tomscz.afi.ws.mappers.MapperType;
import com.tomscz.afswinx.rest.dto.AFClassInfo;

public interface AFRest {
    
    public AFClassInfo generateSkeleton(String entityClass, MapperType mapper, ServletContext servletContext) throws SkeletonException;

}
