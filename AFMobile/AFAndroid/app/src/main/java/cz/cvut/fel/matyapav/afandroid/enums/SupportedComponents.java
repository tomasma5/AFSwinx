package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enumeration of supported components
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *@since 1.0.0..
 */
public enum SupportedComponents {

    FORM("Form"),
    TABLE("Table"), //not yet fully supported
    LIST("List");

    SupportedComponents(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

}
