package cz.cvut.fel.matyapav.afandroid;

import org.json.JSONObject;

/**
 * Created by Pavel on 17.12.2015.
 */
public interface JSONParser {

    public ClassDefinition parse(JSONObject toBeParsed);
}
