package cz.cvut.fel.matyapav.afandroid.parsers;

import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;

/**
 * JSON definition parser interface
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public interface JSONParser {

    ClassDefinition parse(JSONObject toBeParsed);
}
