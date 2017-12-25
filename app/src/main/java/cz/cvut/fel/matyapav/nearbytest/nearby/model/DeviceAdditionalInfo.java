package cz.cvut.fel.matyapav.nearbytest.nearby.model;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class DeviceAdditionalInfo {

    private String informationName;
    private String informationContent;

    public DeviceAdditionalInfo(String informationName, String informationContent) {
        this.informationName = informationName;
        this.informationContent = informationContent;
    }

    public String getInformationName() {
        return informationName;
    }

    public String getInformationContent() {
        return informationContent;
    }

    @Override
    public String toString() {
        return "[ "+informationName + " : " + informationContent + "]";
    }
}
