package cz.cvut.fel.matyapav.afandroid.enums;

/**
 * Created by Pavel on 15.02.2016.
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
