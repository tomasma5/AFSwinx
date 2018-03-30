package model;

/**
 * The enumeration of supported component types
 */
public enum SupportedComponentType {
    FORM ("FORM"),
    TABLE ("TABLE"),
    LIST ("LIST");

    private String name;

    SupportedComponentType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
