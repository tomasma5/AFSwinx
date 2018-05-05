package com.tomscz.afswinx.component.uiproxy;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.builders.ComponentBuilder;

/**
 * Model which holds information about component gathered from proxy application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
public class AFProxyComponentDefinition {

    private int id;
    private String name;
    private SupportedComponents type;
    private ComponentBuilder builder;

    public AFProxyComponentDefinition() {
    }

    public AFProxyComponentDefinition(int id, String name, SupportedComponents type, ComponentBuilder builder) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.builder = builder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupportedComponents getType() {
        return type;
    }

    public void setType(SupportedComponents type) {
        this.type = type;
    }

    public ComponentBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ComponentBuilder builder) {
        this.builder = builder;
    }
}
