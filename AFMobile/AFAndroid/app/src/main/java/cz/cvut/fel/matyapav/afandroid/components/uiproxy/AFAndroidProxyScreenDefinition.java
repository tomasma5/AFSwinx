package cz.cvut.fel.matyapav.afandroid.components.uiproxy;

import android.content.Context;

import com.tomscz.afswinx.component.AFSwinxBuildException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.TableBuilder;
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
    private Map<SupportedComponents, List<AFAndroidProxyComponentDefinition>> componentDefinitions;

    /**
     * Gets FORM builder by component key
     *
     * @param componentKey the component key
     * @return return form builder for key if the screen definition contains such builder, null otherwise
     */
    public FormBuilder getFormBuilderByKey(String componentKey) {
        if (componentDefinitions != null) {
            List<AFAndroidProxyComponentDefinition> formDefinitions = componentDefinitions.get(SupportedComponents.FORM);
            if (formDefinitions != null) {
                for (AFAndroidProxyComponentDefinition definition : formDefinitions) {
                    if (definition.getName().equals(componentKey)) {
                        return (FormBuilder) definition.getBuilder();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets TABLE builder by component key
     *
     * @param componentKey the component key
     * @return return TABLE builder for key if the screen definition contains such builder, null otherwise
     */
    public TableBuilder getTableBuilderByKey(String componentKey) {
        if (componentDefinitions != null) {
            List<AFAndroidProxyComponentDefinition> tableDefinitions = componentDefinitions.get(SupportedComponents.TABLE);
            if (tableDefinitions != null) {
                for (AFAndroidProxyComponentDefinition definition : tableDefinitions) {
                    if (definition.getName().equals(componentKey)) {
                        return (TableBuilder) definition.getBuilder();
                    }
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
            List<AFAndroidProxyComponentDefinition> listDefinitions = componentDefinitions.get(SupportedComponents.LIST);
            if (listDefinitions != null) {
                for (AFAndroidProxyComponentDefinition definition : listDefinitions) {
                    if (definition.getName().equals(componentKey)) {
                        return (ListBuilder) definition.getBuilder();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Adds component definition to screen definition
     *
     * @param componentDefinition the component definition
     */
    public void addComponentDefinition(AFAndroidProxyComponentDefinition componentDefinition) {
        if (componentDefinitions == null) {
            componentDefinitions = new HashMap<>();
        }
        if (componentDefinition.getType().equals(SupportedComponents.FORM)) {
            if (componentDefinitions.get(SupportedComponents.FORM) == null) {
                componentDefinitions.put(SupportedComponents.FORM, new ArrayList<AFAndroidProxyComponentDefinition>());
            }

            componentDefinitions.get(SupportedComponents.FORM).add(componentDefinition);
        } else if (componentDefinition.getType().equals(SupportedComponents.TABLE)) {
            if (componentDefinitions.get(SupportedComponents.TABLE) == null) {
                componentDefinitions.put(SupportedComponents.TABLE, new ArrayList<AFAndroidProxyComponentDefinition>());
            }
            componentDefinitions.get(SupportedComponents.TABLE).add(componentDefinition);
        } else if (componentDefinition.getType().equals(SupportedComponents.LIST)){
            if (componentDefinitions.get(SupportedComponents.LIST) == null) {
                componentDefinitions.put(SupportedComponents.LIST, new ArrayList<AFAndroidProxyComponentDefinition>());
            }
            componentDefinitions.get(SupportedComponents.LIST).add(componentDefinition);
        }
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

    public Map<SupportedComponents, List<AFAndroidProxyComponentDefinition>> getComponentDefinitions() {
        return componentDefinitions;
    }

    public void setComponentDefinitions(Map<SupportedComponents, List<AFAndroidProxyComponentDefinition>> componentDefinitions) {
        this.componentDefinitions = componentDefinitions;
    }
}
