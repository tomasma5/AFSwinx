package com.tomscz.afrest.marshal;

import org.w3c.dom.Document;

import com.tomscz.afrest.commons.SupportedComponents;
public class ModelFactory {
    
    public ModelBuilder createModelBuilder(SupportedComponents viewType, Document content){
        ModelBuilder modelBuilder = null;
        if(viewType.equals(SupportedComponents.FORM)){
            modelBuilder = new FormBuilder(content);
        }
        return modelBuilder;
    }

}
