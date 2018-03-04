package com.tomscz.afswinx.component.builders;

import com.google.gson.JsonSyntaxException;
import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxMenuButton;
import com.tomscz.afswinx.component.uiproxy.AFProxyComponentDefinition;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AFSwinxScreenBuilder {

    private String url;

    private static final String SCREEN_HEADING_KEY = "heading";
    private static final String SCREEN_URL_KEY = "screenUrl";
    private static final String SCREEN_COMPONENTS_ARRAY_KEY = "components";

    private static final String COMPONENT_TYPE_KEY = "type";
    private static final String COMPONENT_NAME_KEY = "name";
    private static final String COMPONENT_CONNECTIONS_KEY = "proxyConnections";

    public AFSwinxScreenBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public AFProxyScreenDefinition getScreenDefinition() throws IOException {
        return getScreenDefinition(null);
    }

    AFProxyScreenDefinition getScreenDefinition(JSONObject screenObject) throws IOException {
        if (screenObject == null) {
            screenObject = retrieveScreenDefinition();
        }
        AFProxyScreenDefinition screenDefinition = new AFProxyScreenDefinition();
        screenDefinition.setHeading(screenObject.getString(SCREEN_HEADING_KEY));
        screenDefinition.setScreenUrl(screenObject.getString(SCREEN_URL_KEY));
        JSONArray components = screenObject.optJSONArray(SCREEN_COMPONENTS_ARRAY_KEY);
        if (components != null) {
            for (int i = 0; i < components.length(); i++) {
                JSONObject component = components.getJSONObject(i);
                AFProxyComponentDefinition componentDefinition = new AFProxyComponentDefinition();
                SupportedComponents componentType = (SupportedComponents) AFRestUtils.getEnumFromString(SupportedComponents.class, component.getString(COMPONENT_TYPE_KEY), true);
                componentDefinition.setName(component.getString(COMPONENT_NAME_KEY));
                componentDefinition.setType(componentType);
                JSONObject connections = component.getJSONObject(COMPONENT_CONNECTIONS_KEY);
                if (SupportedComponents.FORM.equalsName(componentType.name())) {
                    AFSwinxFormBuilder formBuilder = AFSwinx.getInstance().getFormBuilder().initBuilder(componentDefinition.getName(), connections.toString());
                    componentDefinition.setBuilder(formBuilder);
                } else if (SupportedComponents.TABLE.equalsName(componentType.name())) {
                    AFSwinxTableBuilder tableBuilder = AFSwinx.getInstance().getTableBuilder().initBuilder(componentDefinition.getName(), connections.toString());
                    componentDefinition.setBuilder(tableBuilder);
                }

                screenDefinition.addComponentDefinition(componentDefinition);
            }

        }
        return screenDefinition;

    }

    private JSONObject retrieveScreenDefinition() throws IOException {
        if (url != null) {

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

            return new JSONObject(response.toString());
        } else {
            //TODO throw some exception; return null for now
            return null;
        }
    }


}
