package com.tomscz.afrest.marshal.utils;

import com.tomscz.afrest.commons.Constants;

public class DataParserHelper {

    //TODO this method should first check configurations
    public static String getFildSplitter(){
        return Constants.FIELD_SEPARATOR;
    }
    
    public static String getFieldPropertySplitter(){
        return Constants.FIELD_PROPERTY_SEPARATOR;
    }
    
    public static String getPropertyAndValueSplitter(){
        return Constants.FIELD_PROPERTY_VALUE_SEPARATOR;
    }
}
