package com.tomscz.afswinx.marshal;

import com.tomscz.afswinx.common.SupportedComponents;
public class ModelFactory {
    
    public ModelBuilder createModelBuilder(SupportedComponents viewType, String content){
        ModelBuilder modelBuilder = null;
        if(viewType.equals(SupportedComponents.FORM)){
            modelBuilder = new FormBuilder(content);
        }
        return modelBuilder;
    }

}
