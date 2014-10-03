package com.tomscz.afswinx.rest.dto;

import java.io.Serializable;
import java.util.List;

import com.tomscz.afswinx.layout.Layout;

public class AFFieldInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String label;
    private Layout layout;
    private List<AFValidationRule> rules;
    public String getLabel() {
        return label;
    }
    public String getId() {
        return id;
    }
    public Layout getLayout() {
        return layout;
    }
    public List<AFValidationRule> getRules() {
        return rules;
    }
}
