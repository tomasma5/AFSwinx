package cz.cvut.fel.matyapav.afandroid.enums;

import java.io.Serializable;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
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
