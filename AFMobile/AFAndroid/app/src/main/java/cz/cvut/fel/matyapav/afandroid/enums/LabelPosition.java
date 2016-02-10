package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enum for supported positions of label in form
 * Created by Pavel on 25.12.2015.
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
