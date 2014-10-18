package com.tomscz.afswinx.validation.factory;

import java.util.HashMap;

import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.LengthValidator;
import com.tomscz.afswinx.validation.NumberValidator;
import com.tomscz.afswinx.validation.RequiredValidator;

/**
 * This is factory which create concorete validator, based on {@link SupportedValidations} type.
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

    public AFValidations createValidator(SupportedValidations validation,
            HashMap<String, String> parameters) {
        if (validation.equals(SupportedValidations.LENGTH)) {
            LengthValidator validator = new LengthValidator();
            if (parameters != null && parameters.isEmpty()) {
                String maxLengthValue = parameters.get(LengthValidator.MAX_LENGTH_KEY);
                String minLegthValue = parameters.get(LengthValidator.MIN_LENGTH_KEY);
                try {
                    if (maxLengthValue != null) {
                        int maxLegth = Utils.convertStringToInteger(maxLengthValue);
                        validator.setMaxLength(maxLegth);

                    }
                } catch (IllegalArgumentException e) {
                    // DO nothing, default parameters will be taken
                }
                try {
                    if (minLegthValue != null) {
                        int minLegth = Utils.convertStringToInteger(minLegthValue);
                        validator.setMinLength(minLegth);
                    }

                } catch (IllegalArgumentException e) {
                    // DO nothing, default parameters will be taken
                }

            }
            return validator;
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
        return null;
    }
}
