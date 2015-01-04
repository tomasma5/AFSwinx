package com.tomscz.afswinx.rest.rebuild.holder;

/**
 * This class hold concrete variable and his data value.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFData {

    // Its unique key in component structure
    private String key;
    private String value;

    public AFData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
