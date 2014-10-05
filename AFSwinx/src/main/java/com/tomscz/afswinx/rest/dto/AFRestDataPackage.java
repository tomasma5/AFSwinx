package com.tomscz.afswinx.rest.dto;

import java.io.Serializable;

public class AFRestDataPackage implements Serializable{

    private static final long serialVersionUID = 1L;
    private AFClassInfo classInfo;
    
    public AFRestDataPackage(){
        
    }

    public AFRestDataPackage(AFClassInfo classInfo){
        this.setClassInfo(classInfo);
    }

    public AFClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(AFClassInfo classInfo) {
        this.classInfo = classInfo;
    }

}
