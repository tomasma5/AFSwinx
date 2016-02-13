package cz.cvut.fel.matyapav.afandroid.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavel on 26.12.2015.
 */

/**
 * This class defines string constants used in application
 */
public class Constants {

    //JSON PARSING DEFINITION CONSTANTS
    public static final String CLASS_INFO = "classInfo";
    public static final String CLASS_NAME = "name";
    public static final String LAYOUT = "layout";
    public static final String LAYOUT_DEF = "layoutDefinition";
    public static final String LAYOUT_ORIENT = "layoutOrientation";
    public static final String FIELD_INFO = "fieldInfo";
    public static final String WIDGET_TYPE = "widgetType";
    public static final String ID = "id";
    public static final String LABEL = "label";
    public static final String CLASS_TYPE = "classType";
    public static final String READ_ONLY = "readOnly";
    public static final String VISIBLE = "visible";
    public static final String LABEL_POS = "labelPosstion";
    public static final String RULES = "rules";
    public static final String VALIDATION_TYPE = "validationType";
    public static final String VALUE = "value";
    public static final String OPTIONS = "options";
    public static final String INNER_CLASSES = "innerClasses";

    //WIDGET TYPE CONSTANTS
    public static final String TEXTFIELD = "TEXTFIELD";
    public static final String PASSWORD = "PASSWORD";
    public static final String NUMBERFIELD = "NUMBERFIELD";

    //VALIDATIONS
    public static final String REQUIRED = "REQUIRED";
    public static final String MAXLENGHT = "MAXLENGTH";

    //TRANSLATIONS
    public static final Map<String, String> cz = new HashMap<>();
    static{
        cz.put("login.userName", "Uživatelské jméno");
        cz.put("login.password","Heslo");
        cz.put("country.name", "Název");
        cz.put("country.shortCut", "Zkratka");
        cz.put("country.isActive", "Aktivní");
    }
    public static final Map<String, String> en = new HashMap<>();
    static{
        en.put("login.userName", "Username");
        en.put("login.password","Password");
        en.put("country.name", "Name");
        en.put("country.shortCut", "Shortcut");
        en.put("country.isActive", "Active");
    }


}
