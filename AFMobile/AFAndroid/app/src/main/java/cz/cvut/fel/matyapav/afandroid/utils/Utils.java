package cz.cvut.fel.matyapav.afandroid.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;

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

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
