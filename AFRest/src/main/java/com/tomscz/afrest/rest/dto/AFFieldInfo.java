package com.tomscz.afrest.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.Layout;

public class AFFieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private SupportedWidgets widgetType;
    private String id;
    private String label;
    private Layout layout;
    private List<AFValidationRule> rules;
    private List<AFOptions> options;
    
    public AFFieldInfo(){
        this.layout = new Layout();
    }
    
    public void addRule(AFValidationRule rule){
        if(this.rules == null){
            this.rules = new ArrayList<AFValidationRule>();
        }
        this.rules.add(rule);
    }
    
    public void addOption(AFOptions option){
        if(this.options== null){
            this.options = new ArrayList<AFOptions>();
        }
        this.options.add(option);
    }
    
    public void addOption(List<AFOptions> option){
        if(this.options== null){
            this.options = new ArrayList<AFOptions>();
        }
        this.options.addAll(option);
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

    public List<AFOptions> getOptions() {
        return options;
    }

    public void setOptions(List<AFOptions> options) {
        this.options = options;
    }
}
