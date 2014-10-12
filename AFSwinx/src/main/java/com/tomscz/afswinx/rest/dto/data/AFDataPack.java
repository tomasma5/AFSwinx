package com.tomscz.afswinx.rest.dto.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AFDataPack implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AFData> data;
    private String className;

    public AFDataPack(String className) {
        this.className = className;
    }

    public AFDataPack(String className, List<AFData> data) {
        this.className = className;
        this.data = data;
    }

    public List<AFData> getData() {
        return data;
    }

    public void setData(List<AFData> data) {
        this.data = data;
    }

    public void addData(AFData data) {
        if (this.data == null) {
            this.data = new ArrayList<AFData>();
        }
        this.data.add(data);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
