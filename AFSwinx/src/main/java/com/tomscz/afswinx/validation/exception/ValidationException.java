package com.tomscz.afswinx.validation.exception;

/**
 * This exception indicate, that validations on AFSwinx components failed
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class ValidationException extends Exception{

    private static final long serialVersionUID = 1L;

    public ValidationException(String message){
        super(message);
    }
}
