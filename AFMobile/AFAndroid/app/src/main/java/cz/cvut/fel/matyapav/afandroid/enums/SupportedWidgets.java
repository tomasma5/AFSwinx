package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Created by Pavel on 15.02.2016.
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
