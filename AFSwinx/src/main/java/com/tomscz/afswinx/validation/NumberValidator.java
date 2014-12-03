package com.tomscz.afswinx.validation;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate if input is number. This validator doesn't test required condition. But
 * only validate if input text is number. Based on {@link SupportedWidgets} type we are able to
 * validate against Long {@link SupportedWidgets#NUMBERLONGFIELD} , Double
 * {@link SupportedWidgets#NUMBERDOUBLEFIELD}, Integer {@link SupportedWidgets#NUMBERFIELD}. Default
 * validation is against {@link Integer}.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class NumberValidator extends AFBaseValidator {

    protected boolean isIntegerType = false;
    protected boolean isLongType = false;
    protected boolean isDoubleType = false;

    /**
     * Build validator. Decide against which type will be validation done. 
     * @param widgetType type of widget. 
     */
    public NumberValidator(SupportedWidgets widgetType) {
        priority = AFValidatorPriority.NUMBER_PRIORITY;
        if (widgetType == null || widgetType.equals(SupportedWidgets.NUMBERFIELD)) {
            isIntegerType = true;
        } else if (widgetType.equals(SupportedWidgets.NUMBERLONGFIELD)) {
            isLongType = true;
        } else if (widgetType.equals(SupportedWidgets.NUMBERDOUBLEFIELD)) {
            isDoubleType = true;
        } else {
            isIntegerType = true;
        }
    }

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        // If value is null then do nothing
        if (value == null) {
            return;
        }
        // if value is empty then do nothing
        String textValue = (String) value;
        if (textValue.isEmpty()) {
            return;
        }
        // Validate value
        if (isNumber(textValue)) {
            return;
        } else {
            throw new ValidationException(LocalizationUtils.getTextValueFromLocalOrExtendBundle(
                    AFSwinxLocaleConstants.VALIDATION_NUMBER, localization));
        }
    }

    /**
     * This method validate of input is integer or not.
     * 
     * @param value to validate
     * @return true if value is integer false otherwise
     */
    private boolean isNumber(String value) {
        try {
            if (isIntegerType) {
                Integer.parseInt(value);
            } else if (isDoubleType) {
                Double.parseDouble(value);
            } else if (isLongType) {
                Long.parseLong(value);
            }
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            // DO nothing, if throws then this indicate that it is no number
        }
        return false;
    }

}
