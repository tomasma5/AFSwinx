package com.tomscz.afswinx.rest.rebuild;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

/**
 * This is concrete builder which is able to serialize data from server to object which is used to
 * fill data to component and it is able to serialize data from component on object which can be
 * accept by server.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class JSONBuilder extends BaseRestBuilder {

    private static String baseClass = "dummy";

    StringBuilder sb = new StringBuilder();
    AFDataPack dataPack = new AFDataPack(baseClass);
    List<AFDataPack> moreDatas = new ArrayList<AFDataPack>();

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
    public List<AFDataPack> serialize(Object jsonObject) {
        if (jsonObject == null) {
            return moreDatas;
        }
        // Recursively parse received data
        JsonElement element = (JsonElement) jsonObject;
        if (element.isJsonArray()) {
            JsonArray array = (JsonArray) jsonObject;
            for (JsonElement currentElement : array) {
                if (currentElement.isJsonObject()) {
                    JsonObject object = (JsonObject) currentElement;
                    Gson gson = new Gson();
                    JsonObject jsonObjectToSerialize = gson.fromJson(object, JsonObject.class);
                    serializeJSON(jsonObjectToSerialize, "");
                    moreDatas.add(dataPack);
                    dataPack = new AFDataPack(baseClass);
                }
            }
        } else if (element.isJsonObject()) {
            JsonObject object = (JsonObject) jsonObject;
            Gson gson = new Gson();
            JsonObject jsonObjectToSerialize = gson.fromJson(object, JsonObject.class);
            serializeJSON(jsonObjectToSerialize, "");
            moreDatas.add(dataPack);
        }
        return moreDatas;
    }

    /**
     * This method serialize JSON and it's inner object.
     * 
     * @param jsonObject object to serialize.
     * @param fieldKey key of current object.
     */
    private void serializeJSON(JsonObject jsonObject, String fieldKey) {
        for (Map.Entry<String, JsonElement> element : jsonObject.entrySet()) {
            String key = element.getKey();
            JsonElement currentElement = element.getValue();
            if (!currentElement.isJsonNull()) {
                if (currentElement.isJsonObject()) {
                    JsonObject object = (JsonObject) currentElement;
                    serializeJSON(object, Utils.generateKey(fieldKey, key));
                } else if (currentElement.isJsonArray()) {
                    // We don't support arrays in this version
                    continue;
                } else {
                    String value = currentElement.getAsString();
                    AFData data = new AFData(Utils.generateKey(fieldKey, key), value);
                    dataPack.addData(data);
                }
            }
        }
    }

}
