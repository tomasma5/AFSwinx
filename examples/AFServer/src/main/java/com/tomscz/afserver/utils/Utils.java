package com.tomscz.afserver.utils;

public class Utils {

    public static String getJNDIName(String lookupClassName){
        return "java:global/AFServer/"+lookupClassName;
    }
    
}
