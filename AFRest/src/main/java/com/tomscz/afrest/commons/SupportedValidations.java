package com.tomscz.afrest.commons;

public enum SupportedValidations {

    REQUIRED("REQUIRED"),
    NUMBER("NUMBER"),
    CONTAINS("CONTAINS"),
    MINVALUE("MINVALUE"),
    MAXVALUE("MAXVALUE"),
    MINLENGTH("MINLENGTH"),
    MAXLENGTH("MAXLENGTH"),
    RETYPE("RETYPE"),
    LESSTHAN("LESSTHAN");

    private final String name;

    private SupportedValidations(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
    
}
