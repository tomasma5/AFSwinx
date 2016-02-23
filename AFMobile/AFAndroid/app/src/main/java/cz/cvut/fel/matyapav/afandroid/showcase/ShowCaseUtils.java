package cz.cvut.fel.matyapav.afandroid.showcase;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.R;

/**
 * Created by Pavel on 20.02.2016.
 */
public class ShowCaseUtils {

    public static String PREFS_NAME = "preferences";
    public static int PRIVATE_MODE = 0;

    public static void setUserInPreferences(Activity activity, String username, String password){
        SharedPreferences mySharedPreferences= activity.getSharedPreferences(PREFS_NAME, PRIVATE_MODE); //0 is private mode
        SharedPreferences.Editor editor= mySharedPreferences.edit();
        editor.putString("username",username);
        editor.putString("password", password);
        editor.commit();
    }

    public static void clearUserInPreferences(Activity activity){
        SharedPreferences mySharedPreferences = activity.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor= mySharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.commit();
    }

    public static HashMap<String, String> getUserCredentials(Activity activity){
        SharedPreferences mySharedPreferences = activity.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        String username = mySharedPreferences.getString("username", null);
        String password = mySharedPreferences.getString("password", null);
        if(username != null && password != null){
            HashMap<String, String> result = new HashMap<>();
            result.put("username", username);
            result.put("password", password);
            return result;
        }
        return null;
    }

    public static void refreshCurrentFragment(FragmentActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment current = fragmentManager.findFragmentById(R.id.mainLayout);
        fragmentManager.beginTransaction().detach(current).attach(current).commit();
    }
}
