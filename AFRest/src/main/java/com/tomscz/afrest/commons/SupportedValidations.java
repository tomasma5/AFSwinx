package com.tomscz.afrest.commons;

public enum SupportedValidations {

    REQUIRED("required"), LENGTH("length"), NUMBER("number"), CONTAINS("contains"), MIN("minValue"), MAX(
            "maxValue");

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
