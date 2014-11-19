package com.tomscz.afswinx.swing.component;

import com.tomscz.afrest.rest.dto.AFOptions;

public class AFComponentDataHolder extends AFOptions {

    private String valueToDisplay;
    
    public AFComponentDataHolder(String key, String value) {
        super(key, value);
    }
    
    public AFComponentDataHolder(String key, String value, String valueToDisplay){
        super(key, value);
        this.valueToDisplay =  valueToDisplay;
    }

    public String getValueToDisplay() {
        return valueToDisplay;
    }

    public void setValueToDisplay(String valueToDisplay) {
        this.valueToDisplay = valueToDisplay;
    }

}
