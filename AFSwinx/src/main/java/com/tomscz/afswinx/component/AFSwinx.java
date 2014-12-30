package com.tomscz.afswinx.component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.AFSwinxFormBuilder;
import com.tomscz.afswinx.component.builders.AFSwinxTableBuilder;
import com.tomscz.afswinx.component.skin.Skin;

/**
 * This class is facade to using AFSwinx. Use getInstance to get unique instance in your
 * application. It also hold information about localization see
 * {@link AFSwinx#enableLocalization(ResourceBundle)}  and with skin see {@link AFSwinx#setApplicationSkin(Skin)}}for more information.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinx {

    private static AFSwinx instance;

    private HashMap<String, AFSwinxTopLevelComponent> components;

    private ResourceBundle localization;
    
    private Skin applicationSkin;

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
     * This method return builder which must be used to build {@link AFSwinxForm}
     * 
     * @return
     */
    public AFSwinxFormBuilder getFormBuilder() {
        return new AFSwinxFormBuilder();
    }

    /**
     * This method return builder which must be used to build {@link AFSwinxForm}
     * 
     * @return
     */
    public AFSwinxTableBuilder getTableBuilder() {
        return new AFSwinxTableBuilder();
    }

    public void addComponent(AFSwinxTopLevelComponent componentToAdd, String key) {
        removeComponent(key);
        components.put(key, componentToAdd);
    }

    public AFSwinxTopLevelComponent getExistedComponent(String componentName) {
        return components.get(componentName);
    }

    public void removeComponent(String key) {
        AFSwinxTopLevelComponent component = components.get(key);
        if (component != null) {
            components.remove(key);
        }
    }

    public synchronized void removeAllComponents() {
        Set<String> componentsKey = new HashSet<String>();
        for(String key:components.keySet()){
            componentsKey.add(key);
        }
        for(String key:componentsKey){
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
     *        localization is used.
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
    
}
