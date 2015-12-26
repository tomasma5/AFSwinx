package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Created by Marcelka on 25.12.2015.
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
