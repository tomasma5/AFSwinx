package cz.cvut.fel.matyapav.afandroid.components.parts;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
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

    @Override
    public String toString() {
        return "FieldOption{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
