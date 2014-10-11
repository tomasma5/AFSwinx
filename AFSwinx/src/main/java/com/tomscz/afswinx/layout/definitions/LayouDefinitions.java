package com.tomscz.afswinx.layout.definitions;

/**
 * This enum specify all supported layouts
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public enum LayouDefinitions {
        BORDERLAYOUT("BorderLayout"),
        FLOWLAYOUT("FlowLayout");
        
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
