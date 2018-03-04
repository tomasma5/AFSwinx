package com.tomscz.afrest.commons;

/**
 * This {@link Enum} holds supported widget. Widget is input field, label, output field and etc.
 * Based on this {@link Enum} is determined which type of component is used.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum SupportedWidgets {

    TEXTFIELD("TEXTFIELD"),
    LABEL("LABEL"),
    NUMBERFIELD("NUMBERFIELD"),
    NUMBERLONGFIELD("NUMBERLONGFIELD"),
    NUMBERDOUBLEFIELD("NUMBERDOUBLEFIELD"),
    DROPDOWNMENU("DROPDOWNMENU"),
    CHECKBOX("CHECKBOX"),
    TEXTAREA("TEXTAREA"),
    OPTION("OPTION"),
    CALENDAR("CALENDAR"),
    PASSWORD("PASSWORD");

    private final String name;

    private SupportedWidgets(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
    
}
