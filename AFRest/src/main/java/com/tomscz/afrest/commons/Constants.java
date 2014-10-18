package com.tomscz.afrest.commons;

public class Constants {

    public static final String WEB_INF_FOLDER = "/WEB-INF/";
    public static final String ASPECT_FACES_PROPERTY_FILE = "aspectfaces.properties";
    public static final String ASPECT_FACES_RESOURCE_ROOT_FOLDER = Constants.WEB_INF_FOLDER+"/af/";
    public static final String FILE_DIRECTORY_PROPERTY = "filesDirectory";
    public static final String FILE_SWING_MAIN_CONFIG = "structure.config.xml";
    public static final String XML_FILE_TYPE = "xml";
    
    // ///////////////////////////////////////////
    // //////AF model parsing start///////////////
    // ///////////////////////////////////////////

    // String key of variable from inspected components
    public static final String ENTITY_NAME_FIELD = "entityField";
    // Separator which separate two inspected variable in one class
    public static final String FIELD_SEPARATOR = ";fieldStart;";
    // Separator which separate inspected property from variable
    public static final String FIELD_PROPERTY_SEPARATOR = ";";
    // This separator separate key and value of inspected property from variable
    public static final String FIELD_PROPERTY_VALUE_SEPARATOR = ":";

    // AF model parsing end
}
