package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enumeration of supported widget types
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *@since 1.0.0..
 */
public enum SupportedWidgets {

    TEXTFIELD("TEXTFIELD"),
    PASSWORD("PASSWORD"),
    NUMBERFIELD("NUMBERFIELD"),
    NUMBERDOUBLEFIELD("NUMBERDOUBLEFIELD"),
    CALENDAR("CALENDAR"),
    OPTION("OPTION"),
    DROPDOWNMENU("DROPDOWNMENU"),
    CHECKBOX("CHECKBOX");

    private String widgetName;

    SupportedWidgets(String widgetType) {
        this.widgetName = widgetType;
    }

    public String getWidgetName() {
        return widgetName;
    }
}
