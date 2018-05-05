package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Enumeration of supported validation rules
 *
 *@author Pavel Matyáš (matyapav@fel.cvut.cz).
 *@since 1.0.0..
 */
public enum SupportedValidations {

    REQUIRED("REQUIRED"),
    MAXLENGTH("MAXLENGTH"),
    MINVALUE("MINVALUE"),
    MAXVALUE("MAXVALUE"),
    LESSTHAN("LESSTHAN");

    private String validationType;

    SupportedValidations(String validationType) {
        this.validationType = validationType;
    }

    public String getValidationType() {
        return validationType;
    }
}
