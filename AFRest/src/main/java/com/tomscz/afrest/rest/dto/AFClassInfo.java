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
    private List<AFFieldInfo> fieldInfo;
    private List<AFClassInfo> innerClasses;

    public void addFieldInfo(AFFieldInfo fieldInfoToAdd) {
        if (this.fieldInfo == null) {
            this.fieldInfo = new ArrayList<AFFieldInfo>();
        }
        this.fieldInfo.add(fieldInfoToAdd);

    }

    public void addInnerClass(AFClassInfo classInfoToAdd) {
        if (innerClasses == null) {
            innerClasses = new ArrayList<AFClassInfo>();
        }
        innerClasses.add(classInfoToAdd);
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

    public void setOptionsToField(HashMap<String, String> options, String fieldId) {
        ArrayList<AFOptions> afOptions = new ArrayList<AFOptions>();
        for (String key : options.keySet()) {
            afOptions.add(new AFOptions(key, options.get(key)));
        }
        setOptionsToField(afOptions, fieldId);
    }

    private void setOptionsToField(List<AFOptions> options, String fieldId) {
        String[] path = fieldId.split("\\.");
        String fieldInfoId = "";
        boolean findInClasses = false;
        if (path.length > 1) {
            fieldInfoId = path[0];
            findInClasses = true;
        }
        if (findInClasses) {
            for (AFClassInfo currentClass : innerClasses) {
                if (currentClass.name.equals(fieldInfoId)) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 1; i < path.length; i++) {
                        sb.append(path[i]);
                    }
                    currentClass.setOptionsToField(options, sb.toString());
                    return;
                }
            }
        } else {
            for (AFFieldInfo fiedlInfo : fieldInfo) {
                if (fiedlInfo != null && fiedlInfo.getId().equals(fieldId)) {
                    fiedlInfo.addOption(options);
                }
            }
        }
    }

    public void setOptionsToFields(List<String> options, String fieldId) {
        ArrayList<AFOptions> afOtions = new ArrayList<AFOptions>();
        for (String option : options) {
            afOtions.add(new AFOptions(option, option));
        }
        setOptionsToField(afOtions, fieldId);
    }

    public List<AFClassInfo> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(List<AFClassInfo> innerClasses) {
        this.innerClasses = innerClasses;
    }

}
