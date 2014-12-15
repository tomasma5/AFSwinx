package com.tomscz.afrest;

import javax.servlet.ServletContext;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.marshal.ModelInspector;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

public class AFRestGenerator implements AFRest {

    private ServletContext servletContext;

    public AFRestGenerator(ServletContext servletContext){
        this.servletContext = servletContext;
    }

    @Override
    public AFMetaModelPack generateSkeleton(String fullClassName) throws MetamodelException {
        ModelInspector mi = new ModelInspector(this.servletContext);
        return mi.generateModel(fullClassName);
    }
    
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig) throws MetamodelException {
        ModelInspector mi = new ModelInspector(this.servletContext);
        return mi.generateModel(fullClassName,structureConfig,"","");
    }
    
}
