package com.tomscz.afswinx.validation.exception;

import java.util.HashMap;

/**
 * This exception indicate, that validations on AFSwinx components failed
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class ValidationException extends Exception{

    private static final long serialVersionUID = 1L;
    
    private String text;
    private String value;

    public ValidationException(String message){
        this.text = message;
    }
    
    public ValidationException(String message, String value){
        this.text = message;
        this.value = value;
    }
    
    public ValidationException(String message, HashMap<String, String> values){
        this.text = message;
    }
    
    public String getValidationTextToDisplay(){
        String validationText = text;
        if(value != null && !value.isEmpty()){
            validationText = validationText+value;
        }
        return validationText;
    }
}
