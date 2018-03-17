package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.os.AsyncTask;

import com.tomscz.afswinx.rest.connection.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyComponentDefinition;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class AFScreenDefinitionBuilder {

    private Context context;
    private String url;

    private static final String SCREEN_KEY = "key";
    private static final String SCREEN_URL_KEY = "screenUrl";
    private static final String SCREEN_COMPONENTS_ARRAY_KEY = "components";

    private static final String COMPONENT_TYPE_KEY = "type";
    private static final String COMPONENT_NAME_KEY = "name";
    private static final String COMPONENT_CONNECTIONS_KEY = "proxyConnections";

    public AFScreenDefinitionBuilder(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public AFAndroidProxyScreenDefinition getScreenDefinition() throws Exception {
        JSONObject screenObject = new JSONObject(retrieveScreenDefinition());
        AFAndroidProxyScreenDefinition screenDefinition = new AFAndroidProxyScreenDefinition();
        screenDefinition.setKey(screenObject.getString(SCREEN_KEY));

        screenDefinition.setScreenUrl(screenObject.getString(SCREEN_URL_KEY));
        JSONArray components = screenObject.optJSONArray(SCREEN_COMPONENTS_ARRAY_KEY);
        if (components != null) {
            for (int i = 0; i < components.length(); i++) {
                JSONObject component = components.getJSONObject(i);
                AFAndroidProxyComponentDefinition componentDefinition = new AFAndroidProxyComponentDefinition();
                SupportedComponents componentType = SupportedComponents.valueOf(component.getString(COMPONENT_TYPE_KEY));
                componentDefinition.setName(component.getString(COMPONENT_NAME_KEY));
                componentDefinition.setType(componentType);
                JSONObject connections = component.getJSONObject(COMPONENT_CONNECTIONS_KEY);
                if (componentType.equals(SupportedComponents.FORM)) {
                    FormBuilder formBuilder = AFAndroid.getInstance().getFormBuilder().initBuilder(context, componentDefinition.getName(), connections.toString());
                    componentDefinition.setBuilder(formBuilder);
                } else if (componentType.equals(SupportedComponents.TABLE)) {
                    TableBuilder tableBuilder = AFAndroid.getInstance().getTableBuilder().initBuilder(context, componentDefinition.getName(), connections.toString());
                    componentDefinition.setBuilder(tableBuilder);
                }
                if(componentType.equals(SupportedComponents.LIST)) {
                    ListBuilder listBuilder = AFAndroid.getInstance().getListBuilder().initBuilder(context, componentDefinition.getName(), connections.toString());
                    componentDefinition.setBuilder(listBuilder);
                }

                screenDefinition.addComponentDefinition(componentDefinition);
            }
        }
        return screenDefinition;
    }


    private String retrieveScreenDefinition() throws Exception {
        if (url != null) {

            RequestTask task = new RequestTask(url)
                    .setHttpMethod(HttpMethod.GET);

            Object response = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //make it synchronous to wait for response
            if (response instanceof Exception) {
                throw (Exception) response;
            }
            return (String) response;
        } else {
            throw new Exception("Cannot retrieve screen definition because menu endpoint was not specified.");
        }
    }
}
