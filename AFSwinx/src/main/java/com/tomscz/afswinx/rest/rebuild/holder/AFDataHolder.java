package com.tomscz.afswinx.rest.rebuild.holder;

import java.util.HashMap;

public class AFDataHolder {

    private String className;
    private HashMap<String, String> propertiesAndValues = new HashMap<String, String>();
    private HashMap<String, AFDataHolder> innerClasses = new HashMap<String, AFDataHolder>();

    public HashMap<String, AFDataHolder> getInnerClasses() {
        return innerClasses;
    }

    public HashMap<String, String> getPropertiesAndValues() {
        return propertiesAndValues;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
    public void addInnerClass(AFDataHolder data){
        innerClasses.put(data.getClassName(), data);
    }
    
    public void addPropertyAndValue(String propertyName, String value){
        propertiesAndValues.put(propertyName, value);
    }
    
    public AFDataHolder getInnerClassByKey(String key){
        return innerClasses.get(key);
    }
    
}
