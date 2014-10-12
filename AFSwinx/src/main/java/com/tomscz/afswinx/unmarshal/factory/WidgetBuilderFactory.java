package com.tomscz.afswinx.unmarshal.factory;

import com.tomscz.afswinx.common.SupportedWidgets;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;
import com.tomscz.afswinx.unmarshal.builders.InputFieldBuilder;
import com.tomscz.afswinx.unmarshal.builders.LabelFieldBuider;

public class WidgetBuilderFactory {

    public static FieldBuilder createWidgetBuilder(AFFieldInfo fieldInfo){
        if(fieldInfo.getWidgetType().equals(SupportedWidgets.INPUTFIELD)){
            return new InputFieldBuilder();
        }
        else if(fieldInfo.getWidgetType().equals(SupportedWidgets.LABEL)){
            return new LabelFieldBuider();
        }
        else{
            return null;
        }    
    }
    
}
