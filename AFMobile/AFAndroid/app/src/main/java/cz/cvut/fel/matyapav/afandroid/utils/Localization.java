package cz.cvut.fel.matyapav.afandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;
import java.util.ResourceBundle;

import cz.cvut.fel.matyapav.afandroid.utils.SupportedLanguages;

/**
 * Created by Pavel on 13.02.2016.
 */
public class Localization {

    public static String translate(String resource, Context context){
        try {
            int id = context.getResources().getIdentifier(resource, "string", "cz.cvut.fel.matyapav.afandroid");
            return context.getResources().getString(id);
        }catch (Exception e){
            //resource not found
            e.printStackTrace();
            return resource;
        }
    }

    public static void changeLanguage(SupportedLanguages lang, Context context){
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang.getLang().toLowerCase());
        res.updateConfiguration(conf, dm);
    }


}
