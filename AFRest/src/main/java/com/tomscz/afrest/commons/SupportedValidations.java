package com.tomscz.afrest.commons;

public enum SupportedValidations {

    REQUIRED("required"), NUMBER("number"), CONTAINS("contains"), MIN("minValue"), MAX(
            "maxValue"), MINLENGTH("minLength"), MAXLENGTH("maxLength");

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
