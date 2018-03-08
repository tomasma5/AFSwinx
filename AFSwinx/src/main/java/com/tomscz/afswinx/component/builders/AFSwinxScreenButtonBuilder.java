package com.tomscz.afswinx.component.builders;

import com.google.gson.JsonSyntaxException;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxScreenButton;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AFSwinxScreenButtonBuilder {

    private static final String BUTTON_KEY = "key";
    private static final String BUTTON_DISPLAY_TEXT = "displayText";
    private static final String BUTTON_URL_KEY = "url";
    private static final String BUTTON_ORDER_KEY = "menuOrder";

    public AFSwinxScreenButton buildComponent(String key, String displayText, String url) throws AFSwinxBuildException {
        JSONObject menuItem = new JSONObject();
        menuItem.put(BUTTON_KEY, key);
        menuItem.put(BUTTON_DISPLAY_TEXT, displayText);
        menuItem.put(BUTTON_URL_KEY, url);
        return buildComponent(menuItem);
    }

    public AFSwinxScreenButton buildComponent(String key, String url) throws AFSwinxBuildException {
        return buildComponent(key, null, url);
    }


    public AFSwinxScreenButton buildComponent(JSONObject menuItemJsonObj) throws AFSwinxBuildException {
        final AFSwinxScreenButton button = new AFSwinxScreenButton();
        String key = menuItemJsonObj.getString(BUTTON_KEY);
        String displayText = menuItemJsonObj.optString(BUTTON_DISPLAY_TEXT, null);
        final String url = menuItemJsonObj.getString(BUTTON_URL_KEY);
        int menuOrder = menuItemJsonObj.optInt(BUTTON_ORDER_KEY, -1);
        button.setKey(key);
        button.setUrl(url);
        button.setText(displayText != null? displayText : key);
        button.setMenuOrder(menuOrder);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadScreen(button, url);
                ActionListener onClickListener = button.getOnClickListener();
                if (onClickListener != null) {
                    onClickListener.actionPerformed(e);
                }
            }
        });
        return button;
    }

    public void loadScreen(AFSwinxScreenButton button, String screenUrl) {
        try {
            AFProxyScreenDefinition screenDefinition = AFSwinx.getInstance().getScreenDefinitionBuilder(screenUrl).getScreenDefinition();
            ScreenPreparedListener screenPreparedListener = button.getScreenPreparedListener();
            if (screenPreparedListener != null) {
                screenPreparedListener.onScreenPrepared(screenDefinition);
            }
        } catch (JsonSyntaxException | IOException e1) {
            //TODO handle exception
            e1.printStackTrace();
        }
    }


}
