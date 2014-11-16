package com.tomscz.af.showcase.forms;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {

    public static String getLocalizationText(String key) {
        String result;
        try {
            result = getLocalization().getString(key);
        } catch (MissingResourceException e) {
            result = key;
        }
        return result;
    }

    public static ResourceBundle getLocalization() {
        return ResourceBundle.getBundle("en_EN");
    }
}
