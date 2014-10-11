package com.tomscz.afswinx.layout;

import com.tomscz.afswinx.layout.definitions.LabelPosition;
import com.tomscz.afswinx.layout.definitions.LayouDefinitions;

/**
 * This class hold information about layout of particular widget. It should be used for example in
 * form, when specify which layout is used and when label is rendered. This class doesn't support
 * any logic so inner class variables should be in incompatible state.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class Layout {

    private LabelPosition labelPosstion;

    private LayouDefinitions layoutDefinition;

    public LayouDefinitions getLayoutDefinition() {
        return layoutDefinition;
    }

    public void setLayoutDefinition(LayouDefinitions layoutDefinition) {
        this.layoutDefinition = layoutDefinition;
    }

    public LabelPosition getLabelPosstion() {
        return labelPosstion;
    }

    public void setLabelPosstion(LabelPosition labelPosstion) {
        this.labelPosstion = labelPosstion;
    }

}
