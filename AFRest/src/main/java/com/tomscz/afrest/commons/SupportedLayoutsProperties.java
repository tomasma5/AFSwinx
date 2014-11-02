package com.tomscz.afrest.commons;

import com.codingcrayons.aspectfaces.annotation.descriptors.UILayoutDescriptor;

/**
 * This {@link Enum} holds supported layout properties. There should be all layout properties
 * variables which we support. Its are variable in layout section inside widget.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum SupportedLayoutsProperties {

    LAYOUT(UILayoutDescriptor.LAYOUT_AF_VARIABLE), LABELPOSSTION(
            UILayoutDescriptor.LABEL_POSSTION_AF_VARIABLE), LAYOUTORIENTATION(
            UILayoutDescriptor.LAYOUT_ORIENTATION_VARIABLE);

    private final String name;

    private SupportedLayoutsProperties(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}
