package cz.cvut.fel.matyapav.afandroid.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

import cz.cvut.fel.matyapav.afandroid.enums.SupportedLanguages;

/**
 * Localization module - translates strings
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class Localization {

    private static SupportedLanguages currentLanguage;
    private static String pathToStrings; //must be set externally

    public static String translate(Context context, String resource){
        try {
            int id = context.getResources().getIdentifier(resource, "string", pathToStrings);
            System.err.println("Localization for id "+id+" is "+context.getResources().getString(id));
            return context.getResources().getString(id);
        }catch (Exception e){
            System.err.println("Localization text "+resource+" not found");
           // e.printStackTrace();
            return resource;
        }
    }

    public static void changeLanguage(Context context, SupportedLanguages lang){
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang.getLang().toLowerCase());
        res.updateConfiguration(conf, dm);
        currentLanguage = lang;
    }

    public static SupportedLanguages getCurrentLanguage(){
        return currentLanguage;
    }


    public static void setPathToStrings(String path){
        pathToStrings = path;
    }

}
