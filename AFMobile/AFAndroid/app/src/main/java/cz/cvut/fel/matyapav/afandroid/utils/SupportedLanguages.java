package cz.cvut.fel.matyapav.afandroid.utils;

/**
 * Created by Pavel on 13.02.2016.
 */
public enum SupportedLanguages {

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

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
