package com.tomscz.afrest.commons;

/**
 * This {@link Enum} holds supported validation properties. There should be all validation properties
 * variables which we support. Its are variable in validation section inside widget.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum SupportedValidationsProperties {
    
    REQUIRED("required");

    private final String name;

    private SupportedValidationsProperties(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}

