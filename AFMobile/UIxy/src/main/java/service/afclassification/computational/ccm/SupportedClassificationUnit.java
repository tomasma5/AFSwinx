package service.afclassification.computational.ccm;

public enum SupportedClassificationUnit {

    BASIC("Basic classification unit"),
    REQUIRED("Returns for all field required bahaviour no matter the score");

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
