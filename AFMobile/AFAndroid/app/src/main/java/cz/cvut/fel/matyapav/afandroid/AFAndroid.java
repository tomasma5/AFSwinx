package cz.cvut.fel.matyapav.afandroid;

import android.content.Context;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.builders.AFComponentBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.AFMenuBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.AFScreenButtonBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.AFScreenDefinitionBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.TableBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.types.AFScreenButton;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyComponentDefinition;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidScreenPreparedListener;
import cz.cvut.fel.matyapav.afandroid.uiproxy.AndroidUIProxySetup;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.NearbyStatusFacade;
import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.NearbyStatusFacadeBuilder;

/**
 * AFAndroid facade which should be used for operating the framework
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class AFAndroid {

    private static AFAndroid instance = null;
    private HashMap<String, AFComponent> createdComponents;
    private AndroidUIProxySetup proxySetup;
    private NearbyStatusFacade nearbyStatusFacade;

    public AFAndroid() {
        createdComponents = new HashMap<>();
    }

    public static synchronized AFAndroid getInstance() {
        if (instance == null) {
            instance = new AFAndroid();
        }
        return instance;
    }

    public HashMap<String, AFComponent> getCreatedComponents() {
        return createdComponents;
    }

    public void addCreatedComponent(String name, AFComponent component) {
        createdComponents.put(name, component);
    }

    public FormBuilder getFormBuilder() {
        return new FormBuilder();
    }


    public TableBuilder getTableBuilder() {
        return new TableBuilder();
    }

    public ListBuilder getListBuilder() {
        return new ListBuilder();
    }

    /**
     * This method return builder which must be used to build {@link cz.cvut.fel.matyapav.afandroid.components.types.AFMenu}
     *
     * @return new instance of builder which can build menu
     */
    public AFMenuBuilder getMenuBuilder(Context context) {
        return new AFMenuBuilder(context);
    }

    public String getProxyApplicationContext(Context context) {
        return proxySetup.getUiProxyApplicationUuid(context);
    }

    /**
     * This method returs builder which builds {@link AFAndroidProxyScreenDefinition}. This
     * definition contains component definitions {@link AFAndroidProxyComponentDefinition}.
     * Based on the component definition user can get component builders {@link AFComponentBuilder},
     * which can be used for building a {@link AFComponent}, which can be inserted into view
     *
     * @param context   Android context
     * @param screenUrl url of screen definition on UI proxy
     * @return screen definition builder
     */
    public AFScreenDefinitionBuilder getScreenDefinitionBuilder(Context context, String screenUrl, String screenKey) {
        return new AFScreenDefinitionBuilder(context, screenUrl, screenKey);
    }

    /**
     * This method returns builder which builds {@link AFScreenButton}. This button has
     * predefined on click listener. This listener downloads screen definition {@link AFAndroidProxyScreenDefinition}
     * from UI proxy, which contains component definitions {@link AFAndroidProxyComponentDefinition}.
     * After button is clicked a {@link AFAndroidProxyScreenDefinition}
     * is returned to user and can be manipulated through {@link AFAndroidScreenPreparedListener}.
     * Screen definition contains component builders {@link AFComponentBuilder} which can be used
     * for building a {@link AFComponent}, which can be inserted into view
     *
     * @param context Android context
     * @return screen button builder
     */
    public AFScreenButtonBuilder getScreenButtonBuilder(Context context) {
        return new AFScreenButtonBuilder(context);
    }

    public void setProxySetup(AndroidUIProxySetup proxySetup) {
        this.proxySetup = proxySetup;
    }

    public AndroidUIProxySetup getProxySetup() {
        return proxySetup;
    }

    public NearbyStatusFacade getNearbyStatusFacade() {
        return nearbyStatusFacade;
    }

    public void setNearbyStatusFacade(NearbyStatusFacade nearbyStatusFacade) {
        this.nearbyStatusFacade = nearbyStatusFacade;
    }
}
