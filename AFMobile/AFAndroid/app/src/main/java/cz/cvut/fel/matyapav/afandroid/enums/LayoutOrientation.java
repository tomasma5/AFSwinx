package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enum of supported layout orientations for form
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
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
