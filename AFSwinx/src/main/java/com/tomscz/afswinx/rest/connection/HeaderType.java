package com.tomscz.afswinx.rest.connection;

/**
 * This class specify type of supported request and response types.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum HeaderType {
    // XML is not supported in this version, but it will be supported soon.
    XML("application/xml"), JSON("application/json");

    private final String name;

    private HeaderType(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }

}
