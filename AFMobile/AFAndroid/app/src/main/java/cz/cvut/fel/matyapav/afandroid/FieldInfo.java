package cz.cvut.fel.matyapav.afandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class defines properties about field parsed from JSON file
 */
public class FieldInfo {

    private String widgetType;
    private String id;
    private String label;
    private boolean isClass;
    private boolean readOnly;
    private boolean visible;
    private LayoutProperties layout;
    private List<ValidationRule> rules;
    private List<FieldOption> options;

    public FieldInfo() {
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isClass() {
        return isClass;
    }

    public void setIsClass(boolean isClass) {
        this.isClass = isClass;
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
                ", label='" + label + '\'' +
                ", isClass=" + isClass +
                ", readOnly=" + readOnly +
                ", visible=" + visible +
                ", layout=" + layout +
                ", rules=" + rules +
                ", options=" + options +
                '}';
    }
}
