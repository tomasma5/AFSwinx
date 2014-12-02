package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate minimal number length.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class MinAndMaxValueValidator extends AFBaseValidator {

    private Double minValue = Double.MIN_VALUE;
    private Double maxValue = Double.MAX_VALUE;
    private boolean isMinValidator = true;

    /**
     * Constructor to create minimum value validator. It specify minimum value of field.
     * 
     * @param maxLength maximum number of character from validated input.
     */
    public MinAndMaxValueValidator(Double value, boolean isMinValidation) {
        if (isMinValidation) {
            this.minValue = value;
        } else {
            this.maxValue = value;
        }
        this.isMinValidator = isMinValidation;

    }

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        String valueToValidate = (String) value;
        try {
            Double doubleValue = Double.parseDouble(valueToValidate);
            if (isMinValidator) {
                if (doubleValue < minValue) {
                    throw new ValidationException("This field have to value at least " + minValue);
                }
            } else {
                if (doubleValue > maxValue) {
                    throw new ValidationException("This field have to value smaller than "
                            + maxValue);
                }
            }
        } catch (NumberFormatException e) {
            // If value cannot be converted to number, then it is not valid. Hence it should hold
            // value validator, but just for sure
            if (isMinValidator) {
                throw new ValidationException("This field has to value at least than " + minValue);
            }
            throw new ValidationException("This field has to value smaller than " + maxValue);
        }
    }

}
