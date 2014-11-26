package com.tomscz.afrest;
    
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.marshal.ModelInspector;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afrest.rest.dto.data.AFDataPack;

public class AFRestSwing implements AFRest {

    private ServletContext servletContext;

    public AFRestSwing(ServletContext servletContext) throws AFRestException {
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

    @Override
    public AFDataPack generateDataObject(Class<?> clazz, Object objectToGenerate)
            throws IllegalArgumentException {
        if (clazz == null || objectToGenerate == null) {
            throw new IllegalArgumentException("Object to generated and class cant be null");
        }
        AFDataPack data = new AFDataPack(clazz.getName());
        List<Method> getters = getGetters(objectToGenerate.getClass());
        for (int i = 0; i < getters.size(); i++) {
            try {
                String value = String.valueOf(getters.get(i).invoke(objectToGenerate));
                if (value != null && !value.equals("null")) {
                    String key = getters.get(i).getName();
                    char firstLetter = Character.toLowerCase(key.charAt(3));
                    key = key.substring(4);
                    key = firstLetter + key;
                    AFData concreteData = new AFData(key, value);
                    data.addData(concreteData);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return data;
    }

    private List<Method> getGetters(@SuppressWarnings("rawtypes") Class clazz)
            throws SecurityException {
        List<Method> getters = new ArrayList<Method>();

        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
                getters.add(method);
            }
        }
        return getters;
    }
}
