package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enum of supported layout orientations for form
 * Created by Pavel on 25.12.2015.
 */
public enum LayoutOrientation {

    AXISX("AXISX"), AXISY("AXISY");

    private String orientation;

    LayoutOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getName() {
        return orientation;
    }


}
