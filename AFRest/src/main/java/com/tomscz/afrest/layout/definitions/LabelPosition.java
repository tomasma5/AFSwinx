package com.tomscz.afrest.layout.definitions;

/**
 * This enum specify all possible label positions.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public enum LabelPosition {
    
    BEFORE("BEFORE"),
    AFTER("AFTER"),
    NONE("NONE");
    
    private final String name; 
    
    private LabelPosition(String name) {
        this.name = name;
    }
    
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
    
}
