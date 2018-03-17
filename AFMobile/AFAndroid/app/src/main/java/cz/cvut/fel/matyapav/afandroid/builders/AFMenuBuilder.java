package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.rest.connection.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFMenu;
import cz.cvut.fel.matyapav.afandroid.components.types.AFScreenButton;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class AFMenuBuilder {

    private Context context;
    private String url;

    public AFMenuBuilder(Context context) {
        this.context = context;
    }

    public AFMenuBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public AFMenu buildComponent() throws AFSwinxBuildException {
        if (url == null || url.isEmpty()) {
            throw new AFSwinxBuildException("Cannot build menu because the menu definition url was not set.");
        }
        AFMenu menu = new AFMenu();
        try {
            String menuJson = getMenuDefinition(url);
            JSONArray menuItems = new JSONArray(menuJson);
            for (int i = 0; i < menuItems.length(); i++) {
                JSONObject menuItem = menuItems.getJSONObject(i);
                AFScreenButton button = new AFScreenButtonBuilder(context).buildComponent(menuItem);
                menu.addMenuButton(button);
            }
            menu.sort();
            return menu;
        } catch (Exception e) {
            throw new AFSwinxBuildException(e.getMessage());
        }
    }

    private String getMenuDefinition(String url) throws Exception {
        RequestTask task = new RequestTask(url)
                .setHttpMethod(HttpMethod.GET);

        Object response = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //make it synchronous to wait for response
        if (response instanceof Exception) {
            throw (Exception) response;
        }
        return (String) response;
    }

}
