package com.tomscz.afrest.rest.dto;

import java.io.Serializable;

public class AFMetaModelPack implements Serializable{

    private static final long serialVersionUID = 1L;
    private AFClassInfo classInfo;
    
    public AFMetaModelPack(){
        
    }

    public AFMetaModelPack(AFClassInfo classInfo){
        this.setClassInfo(classInfo);
    }

    public AFClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(AFClassInfo classInfo) {
        this.classInfo = classInfo;
    }

}
