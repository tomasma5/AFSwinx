package com.tomscz.afswinx.common;

public enum SupportedWidgets {

   INPUTFIELD("inputField"); 
    
  private final String name; 
    
    private SupportedWidgets(String name) {
        this.name = name;
    }
    
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
