package com.tomscz.afrest;

import javax.servlet.ServletContext;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.marshal.ModelInspector;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

public class AFRestGenerator implements AFRest {

    ModelInspector modelInspector;

    public AFRestGenerator(ServletContext servletContext) {
        this.modelInspector = new ModelInspector(servletContext);
    }

    @Override
    public AFMetaModelPack generateSkeleton(String fullClassName) throws MetamodelException {
        return modelInspector.generateModel(fullClassName);
    }

    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig)
            throws MetamodelException {
        return modelInspector.generateModel(fullClassName, structureConfig, "", "");
    }

    @Override
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig,
            String mainLayout) throws MetamodelException {
        return modelInspector.generateModel(fullClassName, structureConfig, mainLayout, "");
    }

}
