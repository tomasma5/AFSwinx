package cz.cvut.fel.matyapav.afandroid;

import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;


/**
 * Created by Marcelka on 25.12.2015.
 */
public class LayoutProperties {

    private LayoutDefinitions layoutDefinition;
    private LayoutOrientation layoutOrientation;
    private LabelPosition labelPosition;

    public LayoutProperties(){
    }

    public LayoutDefinitions getLayoutDefinition() {
        return layoutDefinition;
    }

    public void setLayoutDefinition(LayoutDefinitions layoutDefinition) {
        this.layoutDefinition = layoutDefinition;
    }

    public LayoutOrientation getLayoutOrientation() {
        return layoutOrientation;
    }

    public void setLayoutOrientation(LayoutOrientation layoutOrientation) {
        this.layoutOrientation = layoutOrientation;
    }

    public LabelPosition getLabelPosition() {
        return labelPosition;
    }

    public void setLabelPossition(LabelPosition labelPosition) {
        this.labelPosition = labelPosition;
    }

    @Override
    public String toString() {
        return "LayoutProperties{" +
                "layoutDefinition=" + layoutDefinition +
                ", layoutOrientation=" + layoutOrientation +
                ", labelPosition=" + labelPosition +
                '}';
    }
}
