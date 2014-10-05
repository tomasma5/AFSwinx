package com.tomscz.afswinx.marshal;

import com.tomscz.afswinx.common.ViewType;

public class ModelFactory {
    
    public ModelBuilder createModelBuilder(ViewType viewType, String content){
        ModelBuilder modelBuilder = null;
        if(viewType.equals(ViewType.FORM)){
            modelBuilder = new FormBuilder(content);
        }
        return modelBuilder;
    }

}
