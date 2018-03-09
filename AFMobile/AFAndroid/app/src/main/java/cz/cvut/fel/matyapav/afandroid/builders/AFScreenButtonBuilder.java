package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.view.View;

import com.tomscz.afswinx.component.AFSwinxBuildException;

import org.json.JSONException;
import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFScreenButton;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidScreenPreparedListener;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class AFScreenButtonBuilder {

    private Context context;

    private static final String BUTTON_KEY = "key";
    private static final String BUTTON_DISPLAY_TEXT = "displayText";
    private static final String BUTTON_URL_KEY = "url";
    private static final String BUTTON_ORDER_KEY = "menuOrder";

    public AFScreenButtonBuilder(Context context) {
        this.context = context;
    }

    public AFScreenButton buildComponent(String key, String displayText, String url) throws AFSwinxBuildException {
        JSONObject menuItem = new JSONObject();
        try {
            menuItem.put(BUTTON_KEY, key);
            menuItem.put(BUTTON_DISPLAY_TEXT, displayText);
            menuItem.put(BUTTON_URL_KEY, url);
        } catch (JSONException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }

        return buildComponent(menuItem);
    }

    public AFScreenButton buildComponent(String key, String url) throws AFSwinxBuildException {
        return buildComponent(key, null, url);
    }


    public AFScreenButton buildComponent(JSONObject menuItemJsonObj) throws AFSwinxBuildException {
        final AFScreenButton button = new AFScreenButton(context);
        try {
            String key = menuItemJsonObj.getString(BUTTON_KEY);

            String displayText = menuItemJsonObj.optString(BUTTON_DISPLAY_TEXT, null);
            final String url = menuItemJsonObj.getString(BUTTON_URL_KEY);
            int menuOrder = menuItemJsonObj.optInt(BUTTON_ORDER_KEY, -1);
            button.setUrl(url);
            button.setText(displayText != null ? displayText : key);
            button.setMenuOrder(menuOrder);
            button.setKey(key);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadScreen(button, url);

                    View.OnClickListener onClickListener = button.getOnClickListener();
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
        } catch (JSONException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }
        return button;
    }

    private void loadScreen(AFScreenButton button, String screenUrl) {
        AFAndroidProxyScreenDefinition screenDefinition = null;
        try {
            screenDefinition = AFAndroid.getInstance().getScreenDefinitionBuilder(context, screenUrl).getScreenDefinition();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot build screen definition. Please check stacktrace");
        }
        AFAndroidScreenPreparedListener screenPreparedListener = button.getScreenPreparedListener();
        if (screenPreparedListener != null) {
            screenPreparedListener.onScreenPrepared(screenDefinition);
        }
    }
}
