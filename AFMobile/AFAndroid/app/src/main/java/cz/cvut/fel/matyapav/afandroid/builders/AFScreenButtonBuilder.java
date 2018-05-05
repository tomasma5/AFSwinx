package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tomscz.afswinx.component.AFSwinxBuildException;

import org.json.JSONException;
import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFScreenButton;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidScreenPreparedListener;
import cz.cvut.fel.matyapav.afandroid.uiproxy.AndroidUIProxySetup;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.NearbyStatusFacade;

/**
 * Builder for buttons which should serve screen definition with prepared builders on click
 *
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

    /**
     * Builds button which will load screen definition from given url on click
     *
     * @param key key of screen which should be got
     * @param displayText text of button
     * @param url url of screen definition
     * @return built button
     * @throws AFSwinxBuildException thrown if json from url from which it is build cannot be parsed
     */
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

    /**
     * Builds button which will load screen definition from given url on click
     * @param key key of screen which should be got
     * @param url url of screen definition
     * @return built button
     * @throws AFSwinxBuildException thrown if json from url from which it is build cannot be parsed
     */
    public AFScreenButton buildComponent(String key, String url) throws AFSwinxBuildException {
        return buildComponent(key, null, url);
    }


    /**
     * Builds button from menu item json definition
     *
     * @param menuItemJsonObj menu item json definition
     * @return built button
     * @throws AFSwinxBuildException thrown if something happened during building process
     */
    public AFScreenButton buildComponent(JSONObject menuItemJsonObj) throws AFSwinxBuildException {
        final AFScreenButton button = new AFScreenButton(context);
        try {
            final String key = menuItemJsonObj.getString(BUTTON_KEY);

            String displayText = menuItemJsonObj.isNull(BUTTON_DISPLAY_TEXT) ? null : menuItemJsonObj.optString(BUTTON_DISPLAY_TEXT, null);
            final String url = menuItemJsonObj.getString(BUTTON_URL_KEY);
            int menuOrder = menuItemJsonObj.optInt(BUTTON_ORDER_KEY, -1);
            button.setUrl(url);
            button.setText((displayText != null && !displayText.isEmpty() && !displayText.equals("null")) ? displayText : key);
            button.setMenuOrder(menuOrder);
            button.setKey(key);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadScreen(button, url, key);
                    if (AFAndroid.getInstance().getNearbyStatusFacade() != null) {
                        AFAndroid.getInstance().getNearbyStatusFacade().runProcess();
                    }
                    if (AFAndroid.getInstance().getProxySetup() != null) {
                        AFAndroid.getInstance().getProxySetup().setLastScreenKey(key);
                    }
                    View.OnClickListener onClickListener = button.getCustomOnClickListener();
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } catch (JSONException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }
        return button;
    }

    private void loadScreen(AFScreenButton button, String screenUrl, String screenKey) {
        AFAndroidProxyScreenDefinition screenDefinition = null;
        try {
            screenDefinition = AFAndroid.getInstance().getScreenDefinitionBuilder(context, screenUrl, screenKey).getScreenDefinition();
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
