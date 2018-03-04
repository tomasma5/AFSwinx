package com.tomscz.afswinx.component.builders;

import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxMenu;
import com.tomscz.afswinx.component.AFSwinxMenuButton;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class AFSwinxMenuBuilder {

    private String url;

    public AFSwinxMenuBuilder setUrl(String url){
        this.url = url;
        return this;
    }

    public AFSwinxMenu buildComponent() throws AFSwinxBuildException, IOException {
        //TODO get menu definition from middleware
        AFSwinxMenu menu = new AFSwinxMenu();
        String menuJson = getMenuDefinition(url);
        JSONArray menuItems = new JSONArray(menuJson);
        for (int i = 0; i < menuItems.length(); i++) {
            JSONObject menuItem = menuItems.getJSONObject(i);
            AFSwinxMenuButton button = new AFSwinxMenuButtonBuilder().buildComponent(menuItem);
            menu.addMenuButton(button);
        }
        menu.sort();
        return menu;
    }

    private String getMenuDefinition(String url) throws IOException {
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
