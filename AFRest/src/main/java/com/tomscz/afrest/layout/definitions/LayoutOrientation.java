package com.tomscz.afrest.layout.definitions;

/**
 * This class specify all available layout orientation. We can orient components via axis X or Y.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum LayoutOrientation {

    AXISX("AxisX"), AXISY("AxisY");

    private final String name;

    private LayoutOrientation(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}
