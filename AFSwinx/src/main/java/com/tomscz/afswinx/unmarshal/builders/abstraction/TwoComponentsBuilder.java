package com.tomscz.afswinx.unmarshal.builders.abstraction;

import javax.swing.JLabel;

import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;

public abstract class TwoComponentsBuilder implements FieldBuilder {

    @Override
    public boolean isBuildAvaiable(AFFieldInfo fieldWithLabel){
        if(fieldWithLabel != null)
            return true;
        return false;
    }
    
    protected JLabel buildSimpleLabel(String text){
        if(text != null && !text.isEmpty()){
            return new JLabel(text);
        }
        return null;
    }
    
}
