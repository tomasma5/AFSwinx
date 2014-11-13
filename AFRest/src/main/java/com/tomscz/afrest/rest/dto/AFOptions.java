package com.tomscz.afrest.rest.dto;

/**
 * This class holds options which will be filled to dropDownMenu, check box and etc. key is key value
 * based in which can be object re-mapped. Value is label like. It is value which should be displayed
 * to user. If key and value is the same then use the same value.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFOptions {

    private String key;
    private String value;

    public AFOptions(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return value;
    }

}
