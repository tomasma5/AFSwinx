package com.tomscz.afswinx.rest.rebuild;

import com.google.gson.JsonObject;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class JSONBuilder extends BaseRestBuilder {

    StringBuilder sb = new StringBuilder();

    @Override
    public Object reselialize(AFDataHolder componentData) {
        JsonObject json = new JsonObject();
        for (String key : componentData.getPropertiesAndValues().keySet()) {
            String value = componentData.getPropertiesAndValues().get(key);
            json.addProperty(key, value);
        }
        for(String childKey:componentData.getInnerClasses().keySet()){
            AFDataHolder value = componentData.getInnerClasses().get(childKey);
            JsonObject jsonInnerClass = (JsonObject) reselialize(value);
            json.add(childKey, jsonInnerClass);
        }
        return json;
    }
}
