package com.tomscz.afserver.utils;

import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;

public class Utils {

    /**
     * This method return lookup name for EJB.
     * @param lookupClassName name of class
     * @return this text: java:global/AFServer/+lookupClassName;
     */
    public static String getJNDIName(String lookupClassName){
        return "java:global/AFServer/"+lookupClassName;
    }

    public static AFFieldInfo getFieldInfoById(AFClassInfo classInfo, String id){
        for (AFFieldInfo fieldInfo: classInfo.getFieldInfo()) {
            if(fieldInfo.getId().equals(id)) {
                return fieldInfo;
            }
        }
        return null;
    }
    
}
