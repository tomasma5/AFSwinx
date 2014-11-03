package com.tomscz.afrest.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomscz.afrest.layout.TopLevelLayout;

public class AFClassInfo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String name;
    private TopLevelLayout layout;
    private List<AFFieldInfo> fieldInfo;

    public void addFieldInfo(AFFieldInfo fieldInfoToAdd){
        if(this.fieldInfo == null){
            this.fieldInfo = new ArrayList<AFFieldInfo>();
        }
        this.fieldInfo.add(fieldInfoToAdd);
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AFFieldInfo> getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(List<AFFieldInfo> fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public TopLevelLayout getLayout() {
        return layout;
    }

    public void setLayout(TopLevelLayout layout) {
        this.layout = layout;
    }

}
