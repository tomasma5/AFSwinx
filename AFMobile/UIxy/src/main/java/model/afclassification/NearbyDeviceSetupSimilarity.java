package model.afclassification;

public enum NearbyDeviceSetupSimilarity {

    SAME(90D, 100D),
    MOSTLY_SIMILAR(70D, 90D),
    MORE_SIMILAR(50D, 70D),
    MORE_DIFFERENT(30D, 50D),
    MOSTLY_DIFFERENT(10D, 30D),
    DIFFERENT(0D, 10D);

    private double thresholdFrom;
    private double thresholdTo;

    NearbyDeviceSetupSimilarity(double thresholdFrom, double thresholdTo) {
        this.thresholdFrom = thresholdFrom;
        this.thresholdTo = thresholdTo;
    }

    public double getThresholdFrom() {
        return thresholdFrom;
    }

    public double getThresholdTo() {
        return thresholdTo;
    }

    public static NearbyDeviceSetupSimilarity getEnumBySimilarityValue(double similarity) {
        for (NearbyDeviceSetupSimilarity enumConstant : NearbyDeviceSetupSimilarity.class.getEnumConstants()) {
            if (similarity > enumConstant.getThresholdFrom() && similarity <= enumConstant.getThresholdTo()) {
                return enumConstant;
            }
        }
        return null;
    }
}
