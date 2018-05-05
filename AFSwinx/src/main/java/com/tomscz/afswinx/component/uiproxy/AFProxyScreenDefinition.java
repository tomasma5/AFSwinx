package com.tomscz.afswinx.component.uiproxy;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
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
    private Map<SupportedComponents, List<AFProxyComponentDefinition>> componentDefinitions;

    /**
     * Gets builder for form with key from screen definition
     *
     * @param componentKey form key
     * @return builder for this form if it is present in screen definition, null otherwise
     */
    public AFSwinxFormBuilder getFormBuilderByKey(String componentKey) {
        if (componentDefinitions != null) {
            List<AFProxyComponentDefinition> formDefinitions = componentDefinitions.get(SupportedComponents.FORM);
            if (formDefinitions != null) {
                for (AFProxyComponentDefinition definition : formDefinitions) {
                    if (definition.getName().equals(componentKey)) {
                        return (AFSwinxFormBuilder) definition.getBuilder();
                    }
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
            List<AFProxyComponentDefinition> tableDefinitions = componentDefinitions.get(SupportedComponents.TABLE);
            if (tableDefinitions != null) {
                for (AFProxyComponentDefinition definition : tableDefinitions) {
                    if (definition.getName().equals(componentKey)) {
                        return (AFSwinxTableBuilder) definition.getBuilder();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Adds component definition to screen definition
     *
     * @param componentDefinition component definition
     */
    public void addComponentDefinition(AFProxyComponentDefinition componentDefinition) {
        if (componentDefinitions == null) {
            componentDefinitions = new HashMap<>();
        }
        if (componentDefinition.getType().equalsName(SupportedComponents.FORM.name())) {
            if (componentDefinitions.get(SupportedComponents.FORM) == null) {
                componentDefinitions.put(SupportedComponents.FORM, new ArrayList<AFProxyComponentDefinition>());
            }

            componentDefinitions.get(SupportedComponents.FORM).add(componentDefinition);
        } else if (componentDefinition.getType().equalsName(SupportedComponents.TABLE.name())) {
            if (componentDefinitions.get(SupportedComponents.TABLE) == null) {
                componentDefinitions.put(SupportedComponents.TABLE, new ArrayList<AFProxyComponentDefinition>());
            }
            componentDefinitions.get(SupportedComponents.TABLE).add(componentDefinition);
        }
    }

    /**
     * Reloads screen definition from server (proxy aplication)
     *
     */
    public void reload(){
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

    public Map<SupportedComponents, List<AFProxyComponentDefinition>> getComponentDefinitions() {
        return componentDefinitions;
    }

    public void setComponentDefinitions(Map<SupportedComponents, List<AFProxyComponentDefinition>> componentDefinitions) {
        this.componentDefinitions = componentDefinitions;
    }
}
