package cz.cvut.fel.matyapav.afandroid.components.uiproxy;

import cz.cvut.fel.matyapav.afandroid.builders.AFComponentBuilder;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class AFAndroidProxyComponentDefinition {
    private int id;
    private String name;
    private SupportedComponents type;
    private AFComponentBuilder builder;

    public AFAndroidProxyComponentDefinition() {
    }

    public AFAndroidProxyComponentDefinition(int id, String name, SupportedComponents type, AFComponentBuilder builder) {
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

    public AFComponentBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(AFComponentBuilder builder) {
        this.builder = builder;
    }
}
