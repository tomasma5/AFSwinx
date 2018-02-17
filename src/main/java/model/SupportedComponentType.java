package model;

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
