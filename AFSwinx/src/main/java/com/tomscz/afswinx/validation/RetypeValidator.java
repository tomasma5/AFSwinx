package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator represent retype validations. Which is used to validate that there are the same
 * input in two field.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class RetypeValidator extends AFBaseValidator {

    private AFSwinxPanel retypePanelToValidate;

    public RetypeValidator(AFSwinxPanel retypePanelToValidate) {
        this.retypePanelToValidate = retypePanelToValidate;
        this.priority = AFValidatorPriority.RETYPE_PRIORITY;
    }

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        if (value == null) {
            return;
        }
        Object anotherValue =
                WidgetBuilderFactory.getInstance()
                        .createWidgetBuilder(retypePanelToValidate.getWidgetType())
                        .getData(retypePanelToValidate);
        if (!anotherValue.equals(value)) {
            throw new ValidationException(LocalizationUtils.getTextValueFromLocalOrExtendBundle(
                    AFSwinxLocaleConstants.VALIDATION_RETYPE, localization));
        }
    }

}
