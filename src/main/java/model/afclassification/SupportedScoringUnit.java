package model.afclassification;

public enum SupportedScoringUnit {

    BASIC("Basic scoring unit"),
    BASIC2("Alternative basic scoring unit");

    private String name;

    SupportedScoringUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
