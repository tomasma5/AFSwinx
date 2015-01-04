package com.tomscz.afswinx.component.builders;

import com.tomscz.afswinx.component.panel.AFSwinxPanel;

/**
 * This class is used to encapsulate {@link AFSwinxPanel} with his id and position. Is used to
 * preserve order of received component.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ComponentDataPacker {

    private int possition;
    private String id;
    private AFSwinxPanel component;

    public ComponentDataPacker(int possition, String id, AFSwinxPanel component) {
        this.possition = possition;
        this.id = id;
        this.component = component;
    }

    public int getPossition() {
        return possition;
    }

    public void setPossition(int possition) {
        this.possition = possition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AFSwinxPanel getComponent() {
        return component;
    }

    public void setComponent(AFSwinxPanel component) {
        this.component = component;
    }

}
