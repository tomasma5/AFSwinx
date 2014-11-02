package com.tomscz.afswinx.unmarshal.factory;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;
import com.tomscz.afswinx.unmarshal.builders.InputFieldBuilder;
import com.tomscz.afswinx.unmarshal.builders.LabelFieldBuider;
import com.tomscz.afswinx.unmarshal.builders.NumberInputBuilder;

public class WidgetBuilderFactory {

    private static WidgetBuilderFactory instance;
    
    private WidgetBuilderFactory(){
        
    }
    
    public static synchronized WidgetBuilderFactory getInstance(){
        if(instance == null){
            instance = new WidgetBuilderFactory();
        }
        return instance;
    }
    
    public FieldBuilder createWidgetBuilder(AFFieldInfo fieldInfo){
       return createWidgetBuilder(fieldInfo.getWidgetType());
    }
    
    public FieldBuilder createWidgetBuilder(SupportedWidgets widget){
        if(widget.equals(SupportedWidgets.INPUTFIELD)){
            return new InputFieldBuilder();
        }
        else if(widget.equals(SupportedWidgets.LABEL)){
            return new LabelFieldBuider();
        }
        else if(widget.equals(SupportedWidgets.NUMBERINPUT)){
            return new NumberInputBuilder();
        }
        else{
            return null;
        }    
    }
    
}
