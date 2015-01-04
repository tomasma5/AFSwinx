package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate is field has any value.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class RequiredValidator extends AFBaseValidator {

    public RequiredValidator() {
        priority = AFValidatorPriority.REQUIRED_PRIORITY;
    }

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        String valueToValidate = (String) value;
        if (valueToValidate != null && !valueToValidate.trim().isEmpty()) {
            return;
        }
        throw new ValidationException(LocalizationUtils.getTextValueFromLocalOrExtendBundle(
                AFSwinxLocaleConstants.VALIDATION_REQUIRED, localization));
    }

}
