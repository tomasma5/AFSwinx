package com.tomscz.afswinx.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AFRestDataPackage implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<AFClassInfo> data;

    public AFRestDataPackage(AFClassInfo item){
        data = new ArrayList<AFClassInfo>();
        data.add(item);
    }
    
    public AFRestDataPackage(List<AFClassInfo> data){
        this.data = data;
    }
    
    public void addItem(AFClassInfo item){
        if(data==null){
            data = new ArrayList<AFClassInfo>();
        }
        data.add(item);
    }
    
    public List<AFClassInfo> getData() {
        return data;
    }

    public void setData(List<AFClassInfo> data) {
        this.data = data;
    }
}
