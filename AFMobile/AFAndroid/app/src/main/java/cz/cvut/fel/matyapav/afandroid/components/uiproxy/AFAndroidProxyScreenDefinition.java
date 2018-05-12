package cz.cvut.fel.matyapav.afandroid.components.uiproxy;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * Model which holds information about screen
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class AFAndroidProxyScreenDefinition {

    private String key;
    private String screenUrl;
    private List<AFAndroidProxyComponentDefinition> componentDefinitions;

    /**
     * Gets FORM builder by component key
     *
     * @param componentKey the component key
     * @return return form builder for key if the screen definition contains such builder, null otherwise
     */
    public FormBuilder getFormBuilderByKey(String componentKey) {
        if (componentDefinitions != null) {
            for (AFAndroidProxyComponentDefinition definition : componentDefinitions) {
                if (definition.getType().equals(SupportedComponents.FORM) && definition.getName().equals(componentKey)) {
                    return (FormBuilder) definition.getBuilder();
                }
            }
        }
        return null;
    }

    /**
     * Gets LIST builder by component key
     *
     * @param componentKey the component key
     * @return return LIST builder for key if the screen definition contains such builder, null otherwise
     */
    public ListBuilder getListBuilderByKey(String componentKey) {
        if (componentDefinitions != null) {
            for (AFAndroidProxyComponentDefinition definition : componentDefinitions) {
                if (definition.getType().equals(SupportedComponents.LIST) && definition.getName().equals(componentKey)) {
                    return (ListBuilder) definition.getBuilder();
                }
            }
        }
        return null;
    }

    /**
     * Builds all component from screen
     *
     * @param connectionParameters basic connectionParameters
     * @return list of prepared components
     */
    public List<AFComponent> buildAllComponents(HashMap<String, String> connectionParameters) {
        List<AFComponent> components = new ArrayList<>();
        ;
        if (componentDefinitions != null) {
            for (AFAndroidProxyComponentDefinition definition : componentDefinitions) {
                AFComponent component = null;
                try {
                    switch (definition.getType()) {
                        case FORM:
                            component = ((FormBuilder) definition.getBuilder())
                                    .setConnectionParameters(connectionParameters)
                                    .createComponent();
                            break;
                        case LIST:
                            component = ((ListBuilder) definition.getBuilder())
                                    .setConnectionParameters(connectionParameters)
                                    .createComponent();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    component = null;
                }
                if (component != null) {
                    components.add(component);
                } else {
                    System.err.println("Something went wrong during build of this component. Component will not be added");
                }
            }
        }
        return components;
    }

    /**
     * Adds component definition to screen definition
     *
     * @param componentDefinition the component definition
     */
    public void addComponentDefinition(AFAndroidProxyComponentDefinition componentDefinition) {
        if (componentDefinitions == null) {
            componentDefinitions = new ArrayList<>();
        }
        componentDefinitions.add(componentDefinition);
    }

    /**
     * Reloads screen definition from server
     *
     * @param context android context
     */
    public void reload(Context context) {
        try {
            AFAndroidProxyScreenDefinition screenDefinition = AFAndroid.getInstance().getScreenDefinitionBuilder(context, screenUrl, key).getScreenDefinition();
            setKey(screenDefinition.key);
            setComponentDefinitions(screenDefinition.getComponentDefinitions());
        } catch (Exception e) {
            System.err.println("Cannot reload screen");
            e.printStackTrace();
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public List<AFAndroidProxyComponentDefinition> getComponentDefinitions() {
        return componentDefinitions;
    }

    public void setComponentDefinitions(List<AFAndroidProxyComponentDefinition> componentDefinitions) {
        this.componentDefinitions = componentDefinitions;
    }
}
