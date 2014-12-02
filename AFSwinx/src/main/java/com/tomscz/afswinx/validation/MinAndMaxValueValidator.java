package com.tomscz.afswinx.validation;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate minimal and maximal value of field. It could be used only on number.
 * Default validation is against {@link Integer}. Based on {@link SupportedWidgets} type we are able
 * to validate against Long {@link SupportedWidgets#NUMBERLONGFIELD} , Double
 * {@link SupportedWidgets#NUMBERDOUBLEFIELD}, Integer {@link SupportedWidgets#NUMBERFIELD}.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class MinAndMaxValueValidator extends NumberValidator {

    // There are specify all possibilities
    private Double minValue = Double.MIN_VALUE;
    private Double maxValue = Double.MAX_VALUE;
    private Long minLongValue = Long.MIN_VALUE;
    private Long maxLongValue = Long.MAX_VALUE;
    private int minIntValue = Integer.MIN_VALUE;
    private int maxIntValue = Integer.MAX_VALUE;
    // This value indicate type of validator if true then it validate minimal value
    private boolean isMinValidator = true;
    // These are string representation on value to which will be validate input value
    private String maxDisplayValue;
    private String minDisplayValue;

    /**
     * Constructor to create minimum value validator. It specify minimum value of field. And based
     * on widget type it determine which type should be used. Default validation is against integer
     * 
     * @param widget of component. Based on them will be validator set up
     * @param value minimal or maximal value.
     * @param isMinValidation if true then minimal value is expected, if false then maximal value is
     *        expected
     * @throws NumberFormatException If value cannot be converted to number then
     *         {@link NumberFormatException} is thrown
     */
    public MinAndMaxValueValidator(SupportedWidgets widget, String value, boolean isMinValidation)
            throws NumberFormatException {
        super(widget);
        if (isMinValidation) {
            if (isDoubleType) {
                this.minValue = Double.parseDouble(value);
            } else if (isIntegerType) {
                this.minIntValue = Integer.parseInt(value);
            } else if (isLongType) {
                this.minLongValue = Long.parseLong(value);
            }
            minDisplayValue = value;
        } else {
            if (isDoubleType) {
                this.maxValue = Double.parseDouble(value);
            } else if (isIntegerType) {
                this.maxIntValue = Integer.parseInt(value);
            } else if (isLongType) {
                this.maxLongValue = Long.parseLong(value);
            }
            maxDisplayValue = value;
        }
        this.isMinValidator = isMinValidation;
    }


    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        String valueToValidate = (String) value;
        // Based on type we tried to convert value to number. If failed then exception is thrown
        try {
            boolean isFailed = false;
            if (isMinValidator) {
                if (isDoubleType) {
                    if (Double.parseDouble(valueToValidate) < minValue) {
                        isFailed = true;
                    }
                } else if (isIntegerType) {
                    if (Integer.parseInt(valueToValidate) < minIntValue) {
                        isFailed = true;
                    }
                } else if (isLongType) {
                    if (Long.parseLong(valueToValidate) < minLongValue) {
                        isFailed = true;
                    }
                }
                if (isFailed)
                    throw new ValidationException(
                            LocalizationUtils
                                    .getTextValueFromLocalOrExtendBundle(
                                            AFSwinxLocaleConstants.VALIDATION_TO_SMALL_NUMBER,
                                            localization)
                                    + minDisplayValue);
            } else {
                if (isDoubleType) {
                    if (Double.parseDouble(valueToValidate) > maxValue) {
                        isFailed = true;
                    }
                } else if (isIntegerType) {
                    if (Integer.parseInt(valueToValidate) > maxIntValue) {
                        isFailed = true;
                    }
                } else if (isLongType) {
                    if (Long.parseLong(valueToValidate) > maxLongValue) {
                        isFailed = true;
                    }
                }
                if (isFailed)
                    throw new ValidationException(
                            LocalizationUtils
                                    .getTextValueFromLocalOrExtendBundle(
                                            AFSwinxLocaleConstants.VALIDATION_TO_SMALL_NUMBER,
                                            localization)
                                    + maxDisplayValue);
            }
        } catch (NumberFormatException e) {
            // If value cannot be converted to number, then it is not valid. Hence it should hold
            // value validator, but just for sure
            if (isMinValidator) {
                throw new ValidationException(
                        LocalizationUtils.getTextValueFromLocalOrExtendBundle(
                                AFSwinxLocaleConstants.VALIDATION_TO_SMALL_NUMBER, localization)
                                + minDisplayValue);
            }
            throw new ValidationException(LocalizationUtils.getTextValueFromLocalOrExtendBundle(
                    AFSwinxLocaleConstants.VALIDATION_TO_GREAT_NUMBER, localization)
                    + maxDisplayValue);
        }
    }

}
