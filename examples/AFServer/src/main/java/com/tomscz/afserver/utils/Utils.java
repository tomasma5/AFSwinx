package com.tomscz.afserver.utils;

public class Utils {

    /**
     * This method return lookup name for EJB.
     * @param lookupClassName name of class
     * @return this text: java:global/AFServer/+lookupClassName;
     */
    public static String getJNDIName(String lookupClassName){
        return "java:global/AFServer/"+lookupClassName;
    }
    
}
