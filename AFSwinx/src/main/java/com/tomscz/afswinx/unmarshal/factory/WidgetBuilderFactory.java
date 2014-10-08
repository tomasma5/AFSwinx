package com.tomscz.afswinx.unmarshal.factory;

import com.tomscz.afswinx.common.SupportedWidgets;
import com.tomscz.afswinx.unmarshal.FieldBuilder;
import com.tomscz.afswinx.unmarshal.InputFieldBuilder;

public class WidgetBuilderFactory {

    public static FieldBuilder createWidgetBuilder(SupportedWidgets widget){
        if(widget.equals(SupportedWidgets.INPUTFIELD)){
            return new InputFieldBuilder();
        }
        return null;        
    }
    
}
