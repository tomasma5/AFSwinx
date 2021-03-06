package com.tomscz.afswinx.component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.*;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.uiproxy.AFProxyComponentDefinition;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import com.tomscz.afswinx.component.uiproxy.UIProxySetup;
import com.tomscz.afswinx.rest.connection.Device;

/**
 * This class is facade to using AFSwinx. Use getInstance to get unique instance in your
 * application. It also hold information about localization see
 * {@link AFSwinx#enableLocalization(ResourceBundle)} and with skin see
 * {@link AFSwinx#setApplicationSkin(Skin)} for more information.
 *
 * @author Martin Tomasek (martin@toms-cz.com)
 * @since 1.0.0.
 */
public class AFSwinx {

    private static AFSwinx instance;

    // All components which could be retrieved
    private HashMap<String, AFSwinxTopLevelComponent> components;

    private ResourceBundle localization;

    private Skin applicationSkin;

    private UIProxySetup proxySetup;

    private AFSwinx() {
        components = new HashMap<>();
    }

    public static synchronized AFSwinx getInstance() {
        if (instance == null) {
            instance = new AFSwinx();
        }
        return instance;
    }

    /**
     * This method return builder which must be used to build {@link AFSwinxForm}.
     *
     * @return new instance of builder, which can build form.
     */
    public AFSwinxFormBuilder getFormBuilder() {
        return new AFSwinxFormBuilder();
    }

    /**
     * This method return builder which must be used to build {@link AFSwinxTable}.
     *
     * @return new instance of builder which can build table.
     */
    public AFSwinxTableBuilder getTableBuilder() {
        return new AFSwinxTableBuilder();
    }


    /**
     * This method return builder which must be used to build {@link AFSwinxMenu}
     *
     * @return new instance of builder which can build menu
     */
    public AFSwinxMenuBuilder getMenuBuilder() {
        return new AFSwinxMenuBuilder();
    }

    /**
     * This method returs builder which builds {@link AFProxyScreenDefinition}. This
     * definition contains component definitions {@link AFProxyComponentDefinition}.
     * Based on the component definition user can get component builders {@link ComponentBuilder},
     * which can be used for building a {@link AFSwinxTopLevelComponent}, which can be inserted into view
     *
     * @param url url of screen definition on UI proxy
     * @param screenKey key of screen
     * @return screen definition builder
     */
    public AFSwinxScreenDefinitionBuilder getScreenDefinitionBuilder(String url, String screenKey) {
        return new AFSwinxScreenDefinitionBuilder(url, screenKey);
    }

    /**
     * This method returns builder which builds {@link AFSwinxScreenButton}. This button has
     * predefined on click listener. This listener downloads screen definition {@link AFProxyScreenDefinition}
     * from UI proxy, which contains component definitions {@link AFProxyComponentDefinition}.
     * After button is clicked a {@link AFProxyScreenDefinition}
     * is returned to user and can be manipulated through {@link ScreenPreparedListener}.
     * Screen definition contains component builders {@link ComponentBuilder} which can be used
     * for building a {@link AFSwinxTopLevelComponent}, which can be inserted into view
     *
     * @return screen button builder
     */
    public AFSwinxScreenButtonBuilder getScreenButtonBuilder() {
        return new AFSwinxScreenButtonBuilder();
    }

    /**
     * This method add component to current hash map, which hold all components which could be
     * retrieved by this class. If there exists components with the same key then component is
     * removed and the new one is added.
     *
     * @param componentToAdd which will be add.
     * @param key            under it will be add. This key also could be used to retrieve this component.
     */
    public void addComponent(AFSwinxTopLevelComponent componentToAdd, String key) {
        removeComponent(key);
        components.put(key, componentToAdd);
    }

    /**
     * This method return component by key. If component was not added before then it null is
     * returned.
     *
     * @param componentKey key of component which will be retrieve.
     * @return component whose key is equal to componentKey which is received as parameter. Null if
     * component does not exist.
     */
    public AFSwinxTopLevelComponent getExistedComponent(String componentKey) {
        return components.get(componentKey);
    }

    /**
     * This method remove component from hash map based on key.
     *
     * @param key of component which will be removed.
     */
    public void removeComponent(String key) {
        AFSwinxTopLevelComponent component = components.get(key);
        if (component != null) {
            components.remove(key);
        }
    }

    /**
     * This method remove all component which were created.
     */
    public synchronized void removeAllComponents() {
        Set<String> componentsKey = new HashSet<String>();
        for (String key : components.keySet()) {
            componentsKey.add(key);
        }
        for (String key : componentsKey) {
            components.remove(key);
        }
    }

    public ResourceBundle getLocalization() {
        return localization;
    }

    /**
     * This method set localization which is used to during building components. This localization
     * is more important then default localization but less important then concrete localization on
     * component. It localize labels and validation messages.
     *
     * @param localization bundle which will be used. Could be null if null, then default
     *                     localization is used.
     */
    public void enableLocalization(ResourceBundle localization) {
        this.localization = localization;
    }

    public Skin getApplicationSkin() {
        return applicationSkin;
    }

    public void setApplicationSkin(Skin applicationSkin) {
        this.applicationSkin = applicationSkin;
    }

    public String getProxyApplicationContext() {
        return proxySetup.getUiProxyApplicationUuid();
    }

    public String getDeviceType() {
        return proxySetup.getDeviceType().toString();
    }

    public void setProxySetup(UIProxySetup proxySetup) {
        this.proxySetup = proxySetup;
    }
}
