package com.tomscz.afswinx.component.builders;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxScreenButton;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Builds buttons which should server screen definitions on its click
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
public class AFSwinxScreenButtonBuilder {

    private static final String BUTTON_KEY = "key";
    private static final String BUTTON_DISPLAY_TEXT = "displayText";
    private static final String BUTTON_URL_KEY = "url";
    private static final String BUTTON_ORDER_KEY = "menuOrder";

    /**
     * Builds the button for screen from given screen key and screen url.
     *
     * @param key screen key
     * @param displayText text which will be displayed on button
     * @param url screen url
     * @return built button
     * @throws AFSwinxBuildException thrown if something went wrong during building process
     */
    public AFSwinxScreenButton buildComponent(String key, String displayText, String url) throws AFSwinxBuildException {
        JSONObject menuItem = new JSONObject();
        menuItem.put(BUTTON_KEY, key);
        menuItem.put(BUTTON_DISPLAY_TEXT, displayText);
        menuItem.put(BUTTON_URL_KEY, url);
        return buildComponent(menuItem);
    }

    /**
     * Builds the button for screen from given screen key and screen url. Screen key will be displayed as button text.
     *
     * @param key screen key
     * @param url screen url
     * @return built button
     * @throws AFSwinxBuildException thrown if something went wrong during building process
     */
    public AFSwinxScreenButton buildComponent(String key, String url) throws AFSwinxBuildException {
        return buildComponent(key, null, url);
    }

    /**
     * Builds the button from menu item json object
     * @param menuItemJsonObj menu item json object
     * @return built button
     * @throws AFSwinxBuildException thrown if something wrong happened during building the button
     */
    public AFSwinxScreenButton buildComponent(JSONObject menuItemJsonObj) throws AFSwinxBuildException {
        final AFSwinxScreenButton button = new AFSwinxScreenButton();
        final String key = menuItemJsonObj.getString(BUTTON_KEY);
        String displayText = menuItemJsonObj.isNull(BUTTON_DISPLAY_TEXT) ? null : menuItemJsonObj.optString(BUTTON_DISPLAY_TEXT, null);
        final String url = menuItemJsonObj.getString(BUTTON_URL_KEY);
        int menuOrder = menuItemJsonObj.optInt(BUTTON_ORDER_KEY, -1);
        button.setKey(key);
        button.setUrl(url);
        button.setText(displayText != null ? displayText : key);
        button.setMenuOrder(menuOrder);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadScreen(button, url, key);
                ActionListener onClickListener = button.getOnClickListener();
                if (onClickListener != null) {
                    onClickListener.actionPerformed(e);
                }
            }
        });
        return button;
    }

    private void loadScreen(AFSwinxScreenButton button, String screenUrl, String screenKey) {
        try {
            AFProxyScreenDefinition screenDefinition = AFSwinx.getInstance().getScreenDefinitionBuilder(screenUrl, screenKey).getScreenDefinition();
            ScreenPreparedListener screenPreparedListener = button.getScreenPreparedListener();
            if (screenPreparedListener != null) {
                screenPreparedListener.onScreenPrepared(screenDefinition);
            }
        } catch (Exception e) {
            System.err.println("Screen cannot be loaded. See stack trace for more details.");
            e.printStackTrace();
        }
    }


}
