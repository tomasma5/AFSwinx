package com.tomscz.afswinx.common;

/**
 * This class holds constants which are used for multiple purposes. For constants which holds model
 * information and which are marshal and unmarshal are used enum types in this package.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxConstants {

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
