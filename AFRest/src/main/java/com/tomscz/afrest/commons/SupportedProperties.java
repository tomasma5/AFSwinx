package com.tomscz.afrest.commons;


/**
 * This {@link Enum} holds supported properties, which can hold variable. There should be all root
 * variables which we support. Root variables are variables without children in widget section
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum SupportedProperties {

    WIDGETTYPE("widgetType"),
    FIELDNAME("fieldName"),
    LABEL("label"),
    OPTIONS("options"),
    READNOLY("readonly"),
    VISIBLE("visible");

    private final String name;

    private SupportedProperties(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
    
}
