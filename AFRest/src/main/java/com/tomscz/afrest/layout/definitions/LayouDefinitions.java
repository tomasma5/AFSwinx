package com.tomscz.afrest.layout.definitions;

/**
 * This enum specify all supported layouts
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public enum LayouDefinitions {
    
        TWOCOLUMNSLAYOUT("TWOCOLUMNSLAYOUT"),
        ONECOLUMNLAYOUT("ONECOLUMNLAYOUT");
        
        private final String name; 
        
        private LayouDefinitions(String name) {
            this.name = name;
        }
        
        public boolean equalsName(String otherName){
            return (otherName == null)? false:name.equals(otherName);
        }

        public String toString(){
           return name;
        }

}
