package com.tomscz.afswinx.rest.rebuild;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
            if (value == null || value.trim().isEmpty()) {
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
    public AFDataPack serialize(Object jsonObject) {
        JsonObject object = (JsonObject) jsonObject;
        Gson gson = new Gson();
        JsonObject jsonObjectToSerialize = gson.fromJson(object, JsonObject.class);
        serializeJseon(jsonObjectToSerialize, "");
        return dataPack;
    }

    private void serializeJseon(JsonObject jsonObject, String fieldKey) {
        for (Map.Entry<String, JsonElement> element : jsonObject.entrySet()) {
            String key = element.getKey();
            JsonElement currentElement = element.getValue();
            if (!currentElement.isJsonNull()) {
                if (currentElement.isJsonObject()) {
                    JsonObject object = (JsonObject) currentElement;
                    serializeJseon(object, Utils.generateKey(fieldKey, key));
                } else {
                    String value = currentElement.getAsString();
                    AFData data = new AFData(Utils.generateKey(fieldKey, key), value);
                    dataPack.addData(data);
                }
            }
        }
    }

}
