package cz.cvut.fel.matyapav.afandroid.enums;

import cz.cvut.fel.matyapav.afandroid.LayoutProperties;

/**
 * Created by Marcelka on 25.12.2015.
 */
public enum LayoutDefinitions {

    TWOCOLUMNSLAYOUT("TWOCOLUMNSLAYOUT"),
    ONECOLUMNLAYOUT("ONECOLUMNLAYOUT");

    private String layoutName;

    LayoutDefinitions(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getLayoutName() {
        return layoutName;
    }



}
