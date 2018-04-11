package service.afclassification.computational.ccm;

public enum SupportedClassificationUnit {

    BASIC("Basic classification unit"),
    BASIC2("Alternative basic classification unit");

    private String name;

    SupportedClassificationUnit(String name) {
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
