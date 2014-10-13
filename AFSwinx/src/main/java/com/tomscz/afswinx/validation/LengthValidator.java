package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This validator validate minimal and maximal length.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
//TODO use property file to language modification
public class LengthValidator implements AFValidations {

    private int minLength = 0;
    private int maxLength = Integer.MAX_VALUE;

    /**
     * Constructor to create lengthValidator. It specify maxLength of field. Default value is
     * mimimalLenght = 0, maximumLenght is integer max value
     */
    public LengthValidator() {

    }

    /**
     * Constructor to create lengthValidator. It specify maxLength of field.
     * 
     * @param maxLength maximum number of character from validated input.
     */
    public LengthValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Constructor to create lengthValidator. It specify maxLength of field.
     * 
     * @param minLength minimum number of character to validate input
     * @param maxLength maximum number of character from validate input
     */
    public LengthValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.minLength = maxLength;
    }

    @Override
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException {
        String valueToValidate = (String) value;
        if (valueToValidate != null && valueToValidate.length() >= minLength
                && valueToValidate.length() <= maxLength) {

            return;
        }
        throw new ValidationException("This field have to at least" + minLength
                + " character and maximum number of characters is " + maxLength);
    }

}
