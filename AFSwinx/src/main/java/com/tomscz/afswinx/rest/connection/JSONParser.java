package com.tomscz.afswinx.rest.connection;

import com.tomscz.afswinx.common.ParameterMissingException;
import org.json.JSONObject;
import org.w3c.dom.Document;

/**
 * This interface specify behavior of XMLParses.
 * 
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 * 
 * @since 1.0.0.
 */
public interface JSONParser {

    /**
     * This method parse JSON string.
     * 
     * @param connections
     * @return data of document. Typically as object of generic type.
     */
    public <T> T parse(JSONObject connections) throws ParameterMissingException;

}
