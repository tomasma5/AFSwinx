package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enum of supported layout definitions for form
 * Created by Pavel on 25.12.2015.
 */
public enum LayoutDefinitions {

    TWOCOLUMNSLAYOUT("TWOCOLUMNSLAYOUT", 2),
    ONECOLUMNLAYOUT("ONECOLUMNLAYOUT", 1);

    private String layoutName;
    private int numberOfColumns;

    LayoutDefinitions(String layoutName, int numberOfColumns) {
        this.layoutName = layoutName;
        this.numberOfColumns = numberOfColumns;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
}
