package com.tomscz.afrest.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tomscz.afrest.layout.TopLevelLayout;

public class AFClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private TopLevelLayout layout;
    private HashMap<String, AFFieldInfo> fieldInfo;

    public void addFieldInfo(AFFieldInfo fieldInfoToAdd) {
        if (this.fieldInfo == null) {
            this.fieldInfo = new HashMap<String, AFFieldInfo>();
        }
        this.fieldInfo.put(fieldInfoToAdd.getId(),fieldInfoToAdd);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String,AFFieldInfo> getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(HashMap<String, AFFieldInfo> fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public TopLevelLayout getLayout() {
        return layout;
    }

    public void setLayout(TopLevelLayout layout) {
        this.layout = layout;
    }

    public void setOptionsToField(HashMap<String, String> options, String fieldId) {
       ArrayList<AFOptions> afOptions = new ArrayList<AFOptions>();
        for(String key:options.keySet()){
           afOptions.add(new AFOptions(key, options.get(key)));
       }
        setOptionsToField(afOptions, fieldId);
    }
    
    private void setOptionsToField(List<AFOptions> options, String fieldId){
        AFFieldInfo fiedlInfo = fieldInfo.get(fieldId);
        if(fiedlInfo != null){
            fiedlInfo.addOption(options);
        }
    }

    public void setOptionsToFields(List<String> options, String fieldId) {
        ArrayList<AFOptions> afOtions = new ArrayList<AFOptions>();
        for (String option : options) {
           afOtions.add(new AFOptions(option, option));
        }
        setOptionsToField(afOtions, fieldId);
    }

}
