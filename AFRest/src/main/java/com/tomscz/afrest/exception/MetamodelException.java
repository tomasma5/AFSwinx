package com.tomscz.afrest.exception;

/**
 * This exception should be thrown when error is occur during marshaling or unmarshaling.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class MetamodelException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public MetamodelException(String message){
        super(message);
    }

}
