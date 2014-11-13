package com.tomscz.afrest.rest.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class AFMetaModelPack implements Serializable {

    private static final long serialVersionUID = 1L;
    private AFClassInfo classInfo;

    public AFMetaModelPack() {

    }

    public AFMetaModelPack(AFClassInfo classInfo) {
        this.setClassInfo(classInfo);
    }

    public AFClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(AFClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public void setOptionsToFields(HashMap<String, String> options, String fieldId) {
        if (classInfo != null) {
            classInfo.setOptionsToField(options, fieldId);
        }
    }

    public void setOptionsToFields(List<String> options, String fieldId) {
        if (classInfo != null) {
            classInfo.setOptionsToFields(options, fieldId);
        }
    }

}
