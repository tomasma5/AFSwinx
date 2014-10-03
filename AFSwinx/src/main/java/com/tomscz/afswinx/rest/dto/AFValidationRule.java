package com.tomscz.afswinx.rest.dto;

import com.tomscz.afswinx.validation.AFSwinxValidations;

public class AFValidationRule {

    private AFSwinxValidations validationType;
    private String value;
    
    public AFValidationRule(AFSwinxValidations validationType, String value){
        this.validationType = validationType;
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    public AFSwinxValidations getValidationType() {
        return validationType;
    }
    
}
