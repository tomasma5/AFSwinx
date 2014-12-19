package com.tomscz.afswinx.rest.connection;

/**
 * This enum holds all supported security method in AFSwinx.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public enum SecurityMethod {
    BASIC("basic");

    private final String name;

    private SecurityMethod(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}
