package com.tomscz.afrest.rest.dto;

import com.tomscz.afrest.commons.SupportedValidations;

/**
 * Thie class represent validation, which will be added to concrete field. It has validation type
 * and value.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFValidationRule {

    private SupportedValidations validationType;
    private String value;

    public AFValidationRule(SupportedValidations validationType, String value) {
        this.validationType = validationType;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public SupportedValidations getValidationType() {
        return validationType;
    }

}
