package com.tomscz.af.showcase.forms;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.tomscz.af.showcase.application.ApplicationContext;

public class Localization {

    public static String getLocalizationText(String key) {
        return getLocalizationText(key, null);
    }

    public static String getLocalizationText(String key, String... parameters) {
        String result;
        try {
            result = getLocalization().getString(key);
            result = MessageFormat.format(result, parameters);
        } catch (MissingResourceException e) {
            result = key;
        }
        return result;
    }

    public static ResourceBundle getLocalization() {
        return ApplicationContext.getInstance().getLocalization();
    }
}
