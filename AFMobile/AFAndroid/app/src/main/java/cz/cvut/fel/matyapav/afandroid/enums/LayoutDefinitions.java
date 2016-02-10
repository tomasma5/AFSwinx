package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enum of supported layout definitions for form
 * Created by Pavel on 25.12.2015.
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
