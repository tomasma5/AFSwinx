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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                //TODO send request to get screen components from given url
                try {
                    String screenJson = getScreenDefinition(url);
                    AFProxyScreenDefinition screenDefinition = AFSwinx.getInstance().getScreenDefinitionBuilder().getScreenDefinition(new JSONObject(screenJson));
                    ScreenPreparedListener screenPreparedListener = button.getScreenPreparedListener();
                    if (screenPreparedListener != null) {
                        screenPreparedListener.onScreenPrepared(screenDefinition);
                    }
                    //execute user defined action listener
                    ActionListener onClickListener = button.getOnClickListener();
                    if (onClickListener != null) {
                        onClickListener.actionPerformed(e);
                    }
                } catch (JsonSyntaxException | IOException e1) {
                    //TODO handle exception
                    e1.printStackTrace();
                }
            }
        });
        return button;
    }


    private String getScreenDefinition(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Application", "4f1eea54-f08b-4f55-bb93-e6d8642abefa"); //TODO move UUID to properties or something

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}
