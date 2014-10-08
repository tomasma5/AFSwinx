package com.tomscz.afswinx.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomscz.afswinx.common.SupportedWidgets;
import com.tomscz.afswinx.layout.Layout;

public class AFFieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private SupportedWidgets widgetType;
    private String id;
    private String label;
    private Layout layout;
    private List<AFValidationRule> rules;
    
    public AFFieldInfo(){
        this.layout = new Layout();
    }
    
    public void addRule(AFValidationRule rule){
        if(this.rules == null){
            this.rules = new ArrayList<AFValidationRule>();
        }
        this.rules.add(rule);
    }

    public List<AFValidationRule> getRules() {
        return rules;
    }

    public void setRules(List<AFValidationRule> rules) {
        this.rules = rules;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SupportedWidgets getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(SupportedWidgets widgetType) {
        this.widgetType = widgetType;
    }
}
