package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate if input is number. This validator doesn't test required condition. But
 * only validate if input text is number.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class NumberValidator extends AFBaseValidator {

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        //If value is null then do nothing
        if (value == null) {
            return;
        }
        //if value is empty then do nothing
        String textValue = (String) value;
        if (textValue.isEmpty()) {
            return;
        }
        //Validate value
        if (isNumber(textValue)) {
            return;
        } else {
            throw new ValidationException(LocalizationUtils.getTextValueFromLocalOrExtendBundle(
                    AFSwinxLocaleConstants.VALIDATION_NUMBER, localization));
        }
    }

    /**
     * This method validate of input is integer or not.
     * @param value to validate
     * @return true if value is integer false otherwise
     */
    private boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            // DO nothing, if throws then this indicate that it is no number
        }
        return false;
    }

}
