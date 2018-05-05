package com.tomscz.afswinx.component.builders;

import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.common.AFSwinxConstants;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.uiproxy.AFProxyComponentDefinition;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Gets screen definition from proxy server and prepares component builders for components in screen
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
public class AFSwinxScreenDefinitionBuilder {

    private String url;
    private String screenKey;

    private static final String SCREEN_KEY = "key";
    private static final String SCREEN_URL_KEY = "screenUrl";
    private static final String SCREEN_COMPONENTS_ARRAY_KEY = "components";

    private static final String COMPONENT_TYPE_KEY = "type";
    private static final String COMPONENT_NAME_KEY = "name";
    private static final String COMPONENT_CONNECTIONS_KEY = "proxyConnections";

    public AFSwinxScreenDefinitionBuilder(String url, String screenKey) {
        this.url = url;
        this.screenKey = screenKey;
    }

    /**
     * Downloads screen definitions and prepared component builders
     *
     * @return screen definition model
     * @throws IOException thrown if something happens during getting definition from server
     * @throws AFSwinxBuildException thrown if something happens during preparation of component builders
     */
    public AFProxyScreenDefinition getScreenDefinition() throws IOException, AFSwinxBuildException {
        JSONObject screenObject = retrieveScreenDefinition();
        AFProxyScreenDefinition screenDefinition = new AFProxyScreenDefinition();
        screenDefinition.setKey(screenObject.getString(SCREEN_KEY));
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

    private JSONObject retrieveScreenDefinition() throws IOException, AFSwinxBuildException {
        if (url != null) {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(AFSwinxConstants.APPLICATION_HEADER, AFSwinx.getInstance().getProxyApplicationContext());
            con.setRequestProperty(AFSwinxConstants.SCREEN_HEADER, screenKey);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        } else {
            throw new AFSwinxBuildException("Cannot get screen definition. URL was not defined");
        }
    }


}
