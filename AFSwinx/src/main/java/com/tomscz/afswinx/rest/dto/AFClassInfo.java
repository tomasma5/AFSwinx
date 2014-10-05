package com.tomscz.afswinx.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "entityInfo")
@XmlType(name = "entityType")
public class AFClassInfo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String name;
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

}
