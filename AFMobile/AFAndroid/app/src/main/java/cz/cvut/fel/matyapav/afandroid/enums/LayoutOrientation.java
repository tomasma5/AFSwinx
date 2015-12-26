package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Created by Marcelka on 25.12.2015.
 */
public enum LayoutOrientation {

    AXISX("AXISX"), AXISY("AXISY");

    private final String orientation;

    private LayoutOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getName() {
        return orientation;
    }


}
