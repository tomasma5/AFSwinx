package com.tomscz.afrest.rest.dto;

import com.tomscz.afrest.commons.SupportedValidations;

public class AFValidationRule {

    private SupportedValidations validationType;
    private String value;
    
    public AFValidationRule(SupportedValidations validationType, String value){
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
