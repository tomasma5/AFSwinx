package com.tomscz.afswinx.validation.factory;

import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.MinAndMaxValueValidator;
import com.tomscz.afswinx.validation.NumberValidator;
import com.tomscz.afswinx.validation.RequiredValidator;

/**
 * This is factory which create concorete validator, based on {@link SupportedValidations} type.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFValidatorFactory {

    private static AFValidatorFactory instance;

    public static synchronized AFValidatorFactory getInstance() {
        if (instance == null) {
            instance = new AFValidatorFactory();
        }
        return instance;
    }

    public AFValidations createValidator(SupportedValidations validation, String value)
            throws NumberFormatException {
        if (validation.equals(SupportedValidations.LENGTH)) {
            return null;
        }
        if (validation.equals(SupportedValidations.REQUIRED)) {
            return new RequiredValidator();
        }
        if (validation.equals(SupportedValidations.NUMBER)) {
            return new NumberValidator();
        }
        if (validation.equals(SupportedValidations.CONTAINS)) {
            return null;
        }
        if (validation.equals(SupportedValidations.MIN)) {
            Double validationValue = Double.parseDouble(value);
            return new MinAndMaxValueValidator(validationValue, true);
        }
        if (validation.equals(SupportedValidations.MAX)) {
            Double validationValue = Double.parseDouble(value);
            return new MinAndMaxValueValidator(validationValue, false);
        }
        return null;
    }

}
