package com.tomscz.afswinx.layout;

import com.tomscz.afswinx.layout.definitions.LabelPossition;
import com.tomscz.afswinx.layout.definitions.LayouDefinitions;

public class Layout {

    private LabelPossition labelPosstion;
    
    private LayouDefinitions layoutDefinition;

    public LayouDefinitions getLayoutDefinition() {
        return layoutDefinition;
    }

    public void setLayoutDefinition(LayouDefinitions layoutDefinition) {
        this.layoutDefinition = layoutDefinition;
    }

    public LabelPossition getLabelPosstion() {
        return labelPosstion;
    }

    public void setLabelPosstion(LabelPossition labelPosstion) {
        this.labelPosstion = labelPosstion;
    }
    
}
