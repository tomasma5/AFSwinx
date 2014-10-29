package com.tomscz.afrest.layout;

import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

/**
 * This class hold information about layout of particular widget. It should be used for example in
 * form, when specify which {@link LayouDefinitions} is used and where label is rendered via
 * {@link LabelPosition}. It also holds {@link LayoutOrientation} This class doesn't support any
 * logic so inner class variables should be in incompatible state.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class Layout {

    private LabelPosition labelPosstion;

    private LayouDefinitions layoutDefinition;
    
    private LayoutOrientation layoutOrientation;

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

    public LayoutOrientation getLayoutOrientation() {
        return layoutOrientation;
    }

    public void setLayoutOrientation(LayoutOrientation layoutOrientation) {
        this.layoutOrientation = layoutOrientation;
    }

}
