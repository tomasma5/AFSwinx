package cz.cvut.fel.matyapav.afandroid;

import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;

/**
 * Created by Pavel on 25.12.2015.
 */
public class Utils {

    //TODO one method to choose enum from string

    public static LayoutDefinitions chooseLayoutDefByName(String layoutName){
        for (LayoutDefinitions layDef : LayoutDefinitions.values()) {
            if(layDef.getLayoutName().equals(layoutName)) {
                return layDef;
            }
        }
        return null;
    }

    public static LayoutOrientation chooseLayoutOrientByName(String orientation){
        for (LayoutOrientation layOrient : LayoutOrientation.values()) {
            if(layOrient.getName().equals(orientation)) {
                return layOrient;
            }
        }
        return null;
    }

    public static LabelPosition chooseLayoutPositionByName(String position){
        for (LabelPosition layPos : LabelPosition.values()) {
            if(layPos.getPosition().equals(position)) {
                return layPos;
            }
        }
        return null;
    }

    public static String translate(String key, String lang){
        if(lang.equals("EN")){
            String translation = Constants.en.get(key);
            return translation != null? translation: key;
        }
        if(lang.equals("CZ")){
            String translation = Constants.cz.get(key);
            return translation != null? translation: key;
        }
        //if language not specified return key back
        return key;
    }
}
