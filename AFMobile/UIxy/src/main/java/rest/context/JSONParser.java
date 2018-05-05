package rest.context;

import org.json.JSONObject;

/**
 * This interface specify behavior of JsonParses.
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 * @since 1.0.0.
 */
public abstract class JSONParser {

    /**
     * This method parse JSON string.
     *
     * @param <T> result model into which it should be parsed
     * @param jsonString json strin to be parsed
     * @return data of document. Typically as object of generic type.
     */
    public abstract <T> T parse(String jsonString);

    /**
     * Return the value mapped by the given key, or {@code null} if not present or null.
     *
     * @param key key of string
     * @param json json object from which should be the string retrieved
     * @return the string or null
     */
    protected String optString(JSONObject json, String key) {
        // http://code.google.com/p/android/issues/detail?id=13830
        if (json.isNull(key))
            return null;
        else
            return json.optString(key, null);
    }


}
