package cz.cvut.fel.matyapav.showcase.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.fragments.LoginFragment;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class ShowCaseUtils {

    public static String PREFS_NAME = "preferences";
    public static int PRIVATE_MODE = 0;

    public static void setUserInPreferences(Activity activity, String username, String password){
        SharedPreferences mySharedPreferences= activity.getSharedPreferences(PREFS_NAME, PRIVATE_MODE); //0 is private mode
        SharedPreferences.Editor editor= mySharedPreferences.edit();
        editor.putString("username", username);
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

    public static String getUserLogin(Activity activity){
        SharedPreferences mySharedPreferences = activity.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        String username = mySharedPreferences.getString("username", null);
        return username;
    }

    public static void refreshCurrentFragment(FragmentActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment current = fragmentManager.findFragmentById(R.id.mainLayout);
        if(current instanceof LoginFragment){
            Menu menu = ((NavigationView) activity.findViewById(R.id.nav_view)).getMenu();
            menu.setGroupVisible(R.id.beforeLoginGroup, true);
            menu.setGroupVisible(R.id.afterLoginGroup, false);
        }
        fragmentManager.beginTransaction().detach(current).attach(current).commit();
    }

    public static int convertDpToPixels(int dps, Context context){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static void showBuildingFailedDialog(Activity activity, Exception e){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle(Localization.translate("error.building.failed"));
        alertDialog.setMessage(Localization.translate("error.reason")+" : " + e.getMessage());
        alertDialog.show();
    }

    public static void connectFormAndList(final String listId, final String formId) {
        //connect list and form
        final AFList list = (AFList) AFAndroid.getInstance().getCreatedComponents().get(listId);
        final AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(formId);

        if (list != null && form != null) {
            list.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    form.insertData(list.getDataFromItemOnPosition(position));
                }
            });
        } else {
            Log.e(ShowCaseUtils.class.getName(), "One or both components were not found, therefore cannot be connected.");
        }
    }

}
