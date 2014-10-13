package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate is field has any value.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
//TODO use property file to language modification
public class RequiredValidator implements AFValidations{

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)throws ValidationException {
        String valueToValidate = (String) value;
        if(valueToValidate != null && valueToValidate.trim().isEmpty()){
            return;
        }
        throw new ValidationException("This field is required");
    }

}
