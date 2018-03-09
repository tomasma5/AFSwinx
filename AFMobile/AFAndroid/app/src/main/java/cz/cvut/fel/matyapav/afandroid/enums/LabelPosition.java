package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enum for supported positions of label in form
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public enum LabelPosition {

    BEFORE("BEFORE"),AFTER("AFTER"), NONE("NONE");

    private String position;

    LabelPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
