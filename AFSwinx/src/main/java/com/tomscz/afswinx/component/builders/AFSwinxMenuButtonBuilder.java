package com.tomscz.afswinx.component.builders;

import com.google.gson.JsonSyntaxException;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxMenuButton;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AFSwinxMenuButtonBuilder {

    private static final String BUTTON_TITLE_KEY = "title";
    private static final String BUTTON_URL_KEY = "url";
    private static final String BUTTON_ORDER_KEY = "menuOrder";

    public AFSwinxMenuButton buildComponent(JSONObject menuItemJsonObj) throws AFSwinxBuildException {
        final AFSwinxMenuButton button = new AFSwinxMenuButton();
        String title = menuItemJsonObj.getString(BUTTON_TITLE_KEY);
        final String url = menuItemJsonObj.getString(BUTTON_URL_KEY);
        int menuOrder = menuItemJsonObj.getInt(BUTTON_ORDER_KEY);
        button.setTitle(title);
        button.setUrl(url);
        button.setText(title);
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

    public void loadScreen(AFSwinxMenuButton button, String screenUrl) {
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
