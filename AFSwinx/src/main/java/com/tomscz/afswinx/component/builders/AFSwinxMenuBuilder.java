package com.tomscz.afswinx.component.builders;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxMenu;
import com.tomscz.afswinx.component.AFSwinxScreenButton;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AFSwinxMenuBuilder {

    private String url;

    public AFSwinxMenuBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public AFSwinxMenu buildComponent() throws AFSwinxBuildException {
        if(url == null || url.isEmpty()) {
            throw new AFSwinxBuildException("Cannot build menu because the menu definition url was not set.");
        }
        AFSwinxMenu menu = new AFSwinxMenu();
        try {
            String menuJson = getMenuDefinition(url);
            JSONArray menuItems = new JSONArray(menuJson);
            for (int i = 0; i < menuItems.length(); i++) {
                JSONObject menuItem = menuItems.getJSONObject(i);
                AFSwinxScreenButton button = new AFSwinxScreenButtonBuilder().buildComponent(menuItem);
                menu.addMenuButton(button);
            }
            menu.sort();
            return menu;
        } catch (IOException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }

    }

    private String getMenuDefinition(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Application", AFSwinx.getInstance().getProxyApplicationContext());

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }


}
