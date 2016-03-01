package cz.cvut.fel.matyapav.afandroid.components.parts;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;

/**
 * This class defines properties about field parsed from JSON file
 */
public class FieldInfo {

    private SupportedWidgets widgetType;
    private String id;
    private String labelText;
    private boolean isInnerClass;
    private boolean readOnly;
    private boolean visible;
    private LayoutProperties layout;
    private List<ValidationRule> rules;
    private List<FieldOption> options;

    public FieldInfo() {
    }

    public SupportedWidgets getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(SupportedWidgets widgetType) {
        this.widgetType = widgetType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public boolean isInnerClass() {
        return isInnerClass;
    }

    public void setIsClass(boolean isClass) {
        this.isInnerClass = isClass;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LayoutProperties getLayout() {
        return layout;
    }

    public void setLayout(LayoutProperties layout) {
        this.layout = layout;
    }

    public void addRule(ValidationRule rule){
        if(rules == null){
            rules = new ArrayList<ValidationRule>();
        }
        rules.add(rule);
    }

    public void addOption(FieldOption option){
        if(options == null){
            options = new ArrayList<FieldOption>();
        }
        options.add(option);
    }

    public List<ValidationRule> getRules() {
        return rules;
    }

    public List<FieldOption> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "widgetType='" + widgetType + '\'' +
                ", id='" + id + '\'' +
                ", labelText='" + labelText + '\'' +
                ", isInnerClass=" + isInnerClass +
                ", readOnly=" + readOnly +
                ", visible=" + visible +
                ", layout=" + layout +
                ", rules=" + rules +
                ", options=" + options +
                '}';
    }
}
