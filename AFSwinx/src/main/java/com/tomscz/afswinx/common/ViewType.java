package com.tomscz.afswinx.common;

public enum ViewType {

    FORM("form");
    
    private final String name; 
    
    private ViewType(String name) {
        this.name = name;
    }
    
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
