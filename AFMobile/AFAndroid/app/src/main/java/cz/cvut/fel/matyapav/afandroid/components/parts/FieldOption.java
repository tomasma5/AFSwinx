package cz.cvut.fel.matyapav.afandroid.components.parts;

/**
 * Created by Pavel on 25.12.2015.
 */
public class FieldOption {

    private String key;
    private String value;

    public FieldOption() {
    }

    public FieldOption(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
