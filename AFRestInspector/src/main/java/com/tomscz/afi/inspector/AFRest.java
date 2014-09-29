package com.tomscz.afi.inspector;

import javax.servlet.ServletContext;

import com.tomscz.afi.dto.AFClassInfo;
import com.tomscz.afi.exceptions.SkeletonException;
import com.tomscz.afi.ws.mappers.MapperType;

public interface AFRest {
    
    public AFClassInfo generateSkeleton(String entityClass, MapperType mapper, ServletContext servletContext) throws SkeletonException;

}
