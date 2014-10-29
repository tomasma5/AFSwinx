package com.tomscz.afrest.commons;

import com.codingcrayons.aspectfaces.annotation.descriptors.UILayoutDescriptor;

/**
 * This {@link Enum} holds supported properties, which can hold variable. There should be all
 * properties which we supports.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum SupportedProperties {

    WIDGETTYPE("widgetType"), FIELDNAME("fieldname"), LAYOUT(UILayoutDescriptor.LAYOUT_AF_VARIABLE), LABEL(
            "label"), REQUIRED("required"), LABELPOSSTION(
            UILayoutDescriptor.LABEL_POSSTION_AF_VARIABLE), LAYOUTORIENTATION(
            UILayoutDescriptor.LAYOUT_ORIENTATION_VARIABLE);

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
