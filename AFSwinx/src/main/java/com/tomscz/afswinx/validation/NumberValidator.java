package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate if input is number.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
//TODO use property file to language modification
public class NumberValidator implements AFValidations {

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        if (value == null) {
            throw new ValidationException("Value to validate cant be null.");
        }
        if (isNumber(value)) {
            return;
        } else {
            throw new ValidationException("This field must have only number values.");
        }
    }
    
    private boolean isNumber(Object value){
        try{
            Integer.parseInt((String) value);
            return true;
        }
        catch(NumberFormatException e){
            
        }
        return false;
    }

}
