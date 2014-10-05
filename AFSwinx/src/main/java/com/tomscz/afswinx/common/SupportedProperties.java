package com.tomscz.afswinx.common;

import com.codingcrayons.aspectfaces.annotation.descriptors.UILayoutDescriptor;

public enum SupportedProperties {
    
    FIELDNAME("fieldname"),
    LAYOUT(UILayoutDescriptor.LAYOUT_AF_VARIABLE),
    LABEL("label"),
    REQUIRED("required"),
    LABELPOSSTION(UILayoutDescriptor.LABEL_POSSTION_AF_VARIABLE);
    
    private final String name; 
    
    private SupportedProperties(String name) {
        this.name = name;
    }
    
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
