package com.tomscz.afrest.ws.mappers;

import java.io.Serializable;

public class MapperType implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String mapping;
    
    public MapperType(){
        
    }
  
    public MapperType(String mapping){
        this.mapping = mapping;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
    
    
}
