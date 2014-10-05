package com.tomscz.afswinx.marshal.utils;

import com.tomscz.afswinx.common.AFSwinxConstants;

public class DataParserHelper {

    //TODO this method should first check configurations
    public static String getFildSplitter(){
        return AFSwinxConstants.FIELD_SEPARATOR;
    }
    
    public static String getFieldPropertySplitter(){
        return AFSwinxConstants.FIELD_PROPERTY_SEPARATOR;
    }
    
    public static String getPropertyAndValueSplitter(){
        return AFSwinxConstants.FIELD_PROPERTY_VALUE_SEPARATOR;
    }
}
