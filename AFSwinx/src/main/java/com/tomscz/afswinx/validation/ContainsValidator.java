package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This is contain validator, which validate if field contains specific value. Values is set during
 * creation.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ContainsValidator extends AFBaseValidator {

    private String containedValue;

    public ContainsValidator(String value) {
        this.containedValue = value;
        priority = AFValidatorPriority.CONTAINS_PRIORITY;
    }

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        boolean isValid = false;
        if (value != null) {
            String valueToVerify = String.valueOf(value);
            if (valueToVerify.contains(containedValue)) {
                isValid = true;
            }
        }
        if (!isValid) {
            throw new ValidationException(LocalizationUtils.getTextValueFromLocalOrExtendBundle(
                    AFSwinxLocaleConstants.VALIDATION_CONTAIN, localization) + containedValue);
        }
    }

}
