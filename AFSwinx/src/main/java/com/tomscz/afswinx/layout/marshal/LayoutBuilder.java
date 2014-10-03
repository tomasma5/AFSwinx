package com.tomscz.afswinx.layout.marshal;

import com.tomscz.afswinx.common.AFSwinxConstants;
import com.tomscz.afswinx.layout.Layout;
import com.tomscz.afswinx.layout.definitions.LabelPossition;
import com.tomscz.afswinx.layout.definitions.LayouDefinitions;

public class LayoutBuilder {    
    
    public String buildLayout(LayouDefinitions layoutDefinitions, LabelPossition labelPosition) {
        Layout layout = new Layout();
        layout.setLayoutDefinition(layoutDefinitions);
        layout.setLabelPosstion(labelPosition);
        return transformLayout(layout);
    }
    
    private String transformLayout(Layout layout){
        StringBuilder sb = new StringBuilder();
        if(layout.getLabelPosstion() != null){
            sb.append(addLine(layout.getLabelPosstion().toString()));
        }
        if(layout.getLayoutDefinition() != null){
            sb.append(addLine(layout.getLayoutDefinition().toString()));
        }
        return sb.toString();
    }
    
    private String addLine(String content){
        return content+AFSwinxConstants.INSIDE_SEPARATOR;
    }
}
