package com.tomscz.afswinx.common;

public enum SupportedComponents {

    FORM("FORM");

    private final String name;

    private SupportedComponents(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}
