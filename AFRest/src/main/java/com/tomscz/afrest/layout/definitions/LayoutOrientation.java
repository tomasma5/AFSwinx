package com.tomscz.afrest.layout.definitions;

public enum LayoutOrientation {

    AXISX("AxisX"),
    AXISY("AxisY");
    
    private final String name; 
    
    private LayoutOrientation(String name) {
        this.name = name;
    }
    
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
