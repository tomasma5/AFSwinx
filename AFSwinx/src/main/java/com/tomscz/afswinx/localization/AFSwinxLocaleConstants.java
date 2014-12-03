package com.tomscz.afswinx.localization;

/**
 * This class holds all constants which are used in localization phase. It provide mostly validation
 * message, which should be override by user and his own localization resource
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxLocaleConstants {

    public static final String DEFAULT_LOCALE_FILE = "AFSwinxDefault";

    //Validation section START
    public static final String VALIDATION_REQUIRED = "validation.required";

    public static final String VALIDATION_NUMBER = "validation.number";
    
    public final static String VALIDATION_TO_SMALL_NUMBER = "validation.number.toSmall";
    public final static String VALIDATION_TO_GREAT_NUMBER = "validation.number.toBig";
    public final static String VALIDATION_LENHTH_TO_SMALL = "validation.length.toSmall";
    public final static String VALIDATION_LENGTH_TO_GREAT = "validation.length.toBig";
    //Validation section EDN
}
