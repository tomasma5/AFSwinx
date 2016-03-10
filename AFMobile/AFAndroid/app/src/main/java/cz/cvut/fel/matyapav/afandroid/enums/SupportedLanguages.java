package cz.cvut.fel.matyapav.afandroid.enums;

import java.io.Serializable;

/**
 * Created by Pavel on 13.02.2016.
 */
public enum SupportedLanguages implements Serializable {

    CZ("cs", "CZ"),
    EN("en", "EN");

    private String lang;
    private String country;

    SupportedLanguages(String lang, String country) {
        this.lang = lang;
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public String getCountry() {
        return country;
    }

}
