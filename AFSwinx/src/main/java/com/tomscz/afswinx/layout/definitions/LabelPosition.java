package com.tomscz.afswinx.layout.definitions;

/**
 * This enum specify all possible label positions.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public enum LabelPosition {
    LEFT("left"),
    RIGHT("right"),
    TOP("top"),
    BOT("bottom"),
    NONE("none");
    
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
