package com.tomscz.afswinx.layout.definitions;

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
