package com.tomscz.afrest.marshal;

import com.tomscz.afrest.commons.SupportedComponents;
public class ModelFactory {
    
    public ModelBuilder createModelBuilder(SupportedComponents viewType, String content){
        ModelBuilder modelBuilder = null;
        if(viewType.equals(SupportedComponents.FORM)){
            modelBuilder = new FormBuilder(content);
        }
        return modelBuilder;
    }

}
