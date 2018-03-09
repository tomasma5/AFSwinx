package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public enum SupportedValidations {

    REQUIRED("REQUIRED"),
    MAXLENGTH("MAXLENGTH"),
    MIN("MIN"),
    MAX("MAX"),
    LESSTHAN("LESSTHAN");

    private String validationType;

    SupportedValidations(String validationType) {
        this.validationType = validationType;
    }

    public String getValidationType() {
        return validationType;
    }
}
