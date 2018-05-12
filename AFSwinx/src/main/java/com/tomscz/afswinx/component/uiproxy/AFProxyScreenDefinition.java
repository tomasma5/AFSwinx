package com.tomscz.afswinx.component.uiproxy;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.AFSwinxFormBuilder;
import com.tomscz.afswinx.component.builders.AFSwinxTableBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model which holds screen definition along with prepared builders for components which should be in screen
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
public class AFProxyScreenDefinition {

    private String key;
    private String screenUrl;
    private List<AFProxyComponentDefinition> componentDefinitions;

    /**
     * Gets builder for form with key from screen definition
     *
     * @param componentKey form key
     * @return builder for this form if it is present in screen definition, null otherwise
     */
    public AFSwinxFormBuilder getFormBuilderByKey(String componentKey) {
        if (componentDefinitions != null) {
            for (AFProxyComponentDefinition definition : componentDefinitions) {
                if (definition.getType().equals(SupportedComponents.FORM) && definition.getName().equals(componentKey)) {
                    return (AFSwinxFormBuilder) definition.getBuilder();
                }
            }
        }
        return null;
    }

    /**
     * Gets builder for table with key from screen definition
     *
     * @param componentKey table key
     * @return builder for this table if it is present in screen defition, null otherwise
     */
    public AFSwinxTableBuilder getTableBuilderByKey(String componentKey) {
        if (componentDefinitions != null) {
            for (AFProxyComponentDefinition definition : componentDefinitions) {
                if (definition.getType().equals(SupportedComponents.TABLE) && definition.getName().equals(componentKey)) {
                    return (AFSwinxTableBuilder) definition.getBuilder();
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
    public List<AFSwinxTopLevelComponent> buildAllComponents(HashMap<String, String> connectionParameters) {
        List<AFSwinxTopLevelComponent> components = new ArrayList<>();
        if (componentDefinitions != null) {
            for (AFProxyComponentDefinition definition : componentDefinitions) {
                AFSwinxTopLevelComponent component = null;
                try {
                    switch (definition.getType()) {
                        case FORM:
                            component = ((AFSwinxFormBuilder) definition.getBuilder())
                                    .setConnectionParameters(connectionParameters)
                                    .buildComponent();
                            break;
                        case TABLE:
                            component = ((AFSwinxTableBuilder) definition.getBuilder())
                                    .setConnectionParameters(connectionParameters)
                                    .buildComponent();
                            break;
                        default:
                            break;
                    }
                } catch (AFSwinxBuildException e) {
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
     * @param componentDefinition component definition
     */

    public void addComponentDefinition(AFProxyComponentDefinition componentDefinition) {
        if (componentDefinitions == null) {
            componentDefinitions = new ArrayList<>();
        }
        componentDefinitions.add(componentDefinition);

    }

    /**
     * Reloads screen definition from server (proxy aplication)
     */
    public void reload() {
        try {
            AFProxyScreenDefinition screenDefinition = AFSwinx.getInstance().getScreenDefinitionBuilder(screenUrl, key).getScreenDefinition();
            setKey(screenDefinition.key);
            setComponentDefinitions(screenDefinition.getComponentDefinitions());
        } catch (IOException | AFSwinxBuildException e) {
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

    public List<AFProxyComponentDefinition> getComponentDefinitions() {
        return componentDefinitions;
    }

    public void setComponentDefinitions(List<AFProxyComponentDefinition> componentDefinitions) {
        this.componentDefinitions = componentDefinitions;
    }
}
