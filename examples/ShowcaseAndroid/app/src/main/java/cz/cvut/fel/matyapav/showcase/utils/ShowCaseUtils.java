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
import cz.cvut.fel.matyapav.showcase.fragments.BaseFragment;
import cz.cvut.fel.matyapav.showcase.fragments.LoginFragment;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class ShowCaseUtils {


    public static void refreshCurrentFragment(FragmentActivity activity, String currentFragmentUrl){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        BaseFragment current = (BaseFragment) fragmentManager.findFragmentById(R.id.mainLayout);
        try {
            current.setScreenDefinition(AFAndroid.getInstance().getScreenDefinitionBuilder(activity, currentFragmentUrl).getScreenDefinition());
            fragmentManager.beginTransaction().detach(current).attach(current).commit();
        } catch (Exception e) {
            System.err.println("Cannot get screen url");
            e.printStackTrace();
        }
    }

    public static int convertDpToPixels(int dps, Context context){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static void showBuildingFailedDialog(Context context, Exception e){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(Localization.translate(context, "error.building.failed"));
        alertDialog.setMessage(Localization.translate(context, "error.reason")+" : " + e.getMessage());
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
