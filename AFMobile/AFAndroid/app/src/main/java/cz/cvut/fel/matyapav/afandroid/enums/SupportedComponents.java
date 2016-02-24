package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Created by Pavel on 13.02.2016.
 */
public enum SupportedComponents {

    FORM("Form"),
    TABLE("Table"),
    LIST("List");

    SupportedComponents(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

}
