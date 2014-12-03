package com.tomscz.afswinx.validation;

/**
 * This class specify validators priority. It is used to better knowledge about priority and to
 * visualize how validator is prior and how will be validation done.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFValidatorPriority {

    public static final int REQUIRED_PRIORITY = Integer.MAX_VALUE;
    public static final int NUMBER_PRIORITY = 100;
    public static final int MIN_AND_MAX_LENGTH_PRIORITY = 70;
    public static final int MIN_AND_MAX_VALUE_PRIORITY = 50;

}
