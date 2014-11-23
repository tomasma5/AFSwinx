package com.tomscz.afswinx.rest.rebuild;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class JSONBuilder extends BaseRestBuilder {

    StringBuilder sb = new StringBuilder();
    AFDataPack dataPack = new AFDataPack("notKnow");

    @Override
    public Object reselialize(AFDataHolder componentData) {
        JsonObject json = new JsonObject();
        for (String key : componentData.getPropertiesAndValues().keySet()) {
            String value = componentData.getPropertiesAndValues().get(key);
            // Check if value is only empty string. if so then set to null, null value can be
            // re-mapped to object but this "" wont
            if (value != null && value.trim().isEmpty()) {
                continue;
            }
            json.addProperty(key, value);
        }
        for (String childKey : componentData.getInnerClasses().keySet()) {
            AFDataHolder value = componentData.getInnerClasses().get(childKey);
            JsonObject jsonInnerClass = (JsonObject) reselialize(value);
            json.add(childKey, jsonInnerClass);
        }
        return json;
    }
    
    @Override 
    public AFDataPack serialize(Object jsonObject){
        serializeJseon(jsonObject, "");
        return dataPack;
    }
    
    private void serializeJseon(Object jsonObject, String fieldKey){
        @SuppressWarnings("unchecked")
        LinkedTreeMap<String, Object> rootMap = (LinkedTreeMap<String, Object>) jsonObject;
        for(String key:rootMap.keySet()){
            Object currentObject = rootMap.get(key);
            if(currentObject!=null){
                String value = rootMap.get(key).toString(); 
                if(value.contains("{")){
                    try{
                        @SuppressWarnings("unchecked")
                        LinkedTreeMap<String, Object> currentSteetMap = (LinkedTreeMap<String, Object>) currentObject;
                        serializeJseon(currentSteetMap, Utils.generateKey(fieldKey,key));
                    }
                    catch(Exception e){
                        AFData data = new AFData(Utils.generateKey(fieldKey,key),value);
                        dataPack.addData(data);
                    }
                }        
                else{
                    AFData data = new AFData(Utils.generateKey(fieldKey,key),value);
                    dataPack.addData(data);
                }
            }
        }
    }

}
