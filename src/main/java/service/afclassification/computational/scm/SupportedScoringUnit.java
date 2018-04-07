package service.afclassification.computational.scm;

public enum SupportedScoringUnit {

    BASIC("Basic scoring unit",
            "Dummy scoring unit which always return 100 for system identification and critical fields and 80 " +
                    "for other cases"
    ),
    NEARBY_DEVICE_SCORING(
            "Nearby devices scoring unit.",
            "Basic scoring using nearby devices. Checks last record of the same action and if nearby devices are " +
                    "similar, it will require or validate some fields less likely."
    ),
    BATTERY_AND_CONNECTION_SCORING(
            "Battery and connection type scoring unit",
            "If client has low battery level, needed and nice to have information mining fields are less " +
                    "likely to be displayed. If user is on wifi field is more likely to be processed rather " +
                    "than on mobile connection."
    );

    private String name;
    private String description;

    SupportedScoringUnit(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
