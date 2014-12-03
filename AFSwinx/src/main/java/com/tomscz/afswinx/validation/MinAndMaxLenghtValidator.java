package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

public class MinAndMaxLenghtValidator extends AFBaseValidator{

    public int value;
    public boolean isMinValidation = false;
    
    public MinAndMaxLenghtValidator(String value, boolean isMinValidation){
        priority = AFValidatorPriority.MIN_AND_MAX_LENGTH_PRIORITY;
        this.value = Integer.parseInt(value);
        this.isMinValidation = isMinValidation;;
    }
    
    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
       String valueToValidate = (String) value;
       if(isMinValidation){
           if(valueToValidate == null || valueToValidate.length() < this.value){
               throw new ValidationException(
                   LocalizationUtils
                           .getTextValueFromLocalOrExtendBundle(
                                   AFSwinxLocaleConstants.VALIDATION_LENHTH_TO_SMALL,
                                   localization)
                           + this.value);
           }
       }
       else{
           if(valueToValidate != null && valueToValidate.length() > this.value){
               throw new ValidationException(
                   LocalizationUtils
                           .getTextValueFromLocalOrExtendBundle(
                                   AFSwinxLocaleConstants.VALIDATION_LENGTH_TO_GREAT,
                                   localization)
                           + this.value);
           }
       }
    }

}
