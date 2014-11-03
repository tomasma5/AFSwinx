package com.tomscz.afrest.layout;

import java.io.Serializable;

import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

public class TopLevelLayout implements Serializable{

    private static final long serialVersionUID = 1L;

    private LayouDefinitions layoutDefinition;
    
    private LayoutOrientation layoutOrientation;

    public LayouDefinitions getLayoutDefinition() {
        return layoutDefinition;
    }

    public void setLayoutDefinition(LayouDefinitions layoutDefinition) {
        this.layoutDefinition = layoutDefinition;
    }

    public LayoutOrientation getLayoutOrientation() {
        return layoutOrientation;
    }

    public void setLayoutOrientation(LayoutOrientation layoutOrientation) {
        this.layoutOrientation = layoutOrientation;
    }
    
}
