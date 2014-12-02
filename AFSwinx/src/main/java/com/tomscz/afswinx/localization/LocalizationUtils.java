package com.tomscz.afswinx.localization;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class is used to transform text to text based on current localization.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class LocalizationUtils {

    /**
     * This method return string from bundle based on bundle.
     * 
     * @param text which will be found in bundle
     * @param bundle in which will be found text
     * @return text in resource bundle or only text given in parameter if text couldn't be found in
     *         bundle or bundle is null
     */
    public static String getTextValueFromLocalOrExtendBundle(String text, ResourceBundle bundle) {
        return getTextFromBundle(bundle, null, text, null);
    }

    /**
     * This method return string from bundle based on bundle.
     * 
     * @param text which will be found in bundle
     * @param bundle in which will be found text
     * @param tags another parameters which will be given to bundle in order in which was received
     * @return text in resource bundle or only text given in parameter if text couldn't be found in
     *         bundle or bundle is null
     */
    public static String getTextFromExtendBundle(String text, ResourceBundle bundle,
            ArrayList<String> tags) {
        return getTextFromBundle(text, bundle, tags);
    }

    /**
     * This method return string from bundle based on bundle. See
     * {@link LocalizationUtils#chooseBundle(ResourceBundle, ResourceBundle, ResourceBundle)} for
     * more detail about choosing process. If text is not found there then default AFSwinxBundle is
     * tried to found string
     * 
     * @param formBundle form bundle
     * @param afSwinxBundle global swinx bundle
     * @param text which will be found in bundle
     * @param tags another parameters which will be given to bundle in order in which was received
     * @return see {@link LocalizationUtils#getTextFromBundle(String, ResourceBundle, ArrayList)}
     *         for more details about return
     */
    private static String getTextFromBundle(ResourceBundle formBundle,
            ResourceBundle afSwinxBundle, String text, ArrayList<String> tags) {
        try {
            ResourceBundle bundle = chooseBundle(formBundle, afSwinxBundle, createDefaultBundle());
            String textFromBundle = getTextFromBundle(text, bundle, tags);
            // Try default resource bundle. Maybe there are few strings which are not covered yet
            if (textFromBundle.equals(text)) {
                textFromBundle = getTextFromBundle(text, createDefaultBundle(), tags);
            }
            return textFromBundle;
        } catch (IllegalArgumentException e) {
            return text;
        }
    }

    /**
     * This method find text in bundle and return it. If bundle is null or text is not found then
     * input text will be returned
     * 
     * @param text to find in bundle
     * @param bundle bundle in which will be found text
     * @param tags another parameters which will be given to bundle in order in which was received
     * @return text in bundle or only text given in parameter if bundle doesn't exists or text
     *         couldn't be found in that bundle
     */
    private static String getTextFromBundle(String text, ResourceBundle bundle,
            ArrayList<String> tags) {
        if (bundle == null || text == null || text.trim().isEmpty()) {
            return text;
        }
        try {
            text = bundle.getString(text);
        } catch (MissingResourceException | NullPointerException | ClassCastException e) {
            return text;
        }
        return text;
    }

    /**
     * This method decide which bundle is used, it simply tests bundle given in parameters and if
     * one of them is not null then used it.
     * 
     * @param bundle which will be checked if not null then bundle is used
     * @return first bundle in array which is not null
     * @throws IllegalArgumentException if there are only null bundle
     */
    private static ResourceBundle chooseBundle(ResourceBundle... bundle)
            throws IllegalArgumentException {
        for (ResourceBundle resourceBundle : bundle) {
            if (resourceBundle != null) {
                return resourceBundle;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * This method create default resource bundle which is implemented in this framework
     * 
     * @return framework default resource bundle
     */
    private static ResourceBundle createDefaultBundle() {
        ResourceBundle defaultBundle =
                ResourceBundle.getBundle(AFSwinxLocaleConstants.DEFAULT_LOCALE_FILE);
        return defaultBundle;
    }

}
