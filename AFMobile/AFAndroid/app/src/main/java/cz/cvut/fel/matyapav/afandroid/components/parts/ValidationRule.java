package cz.cvut.fel.matyapav.afandroid.components.parts;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class ValidationRule {

    private String validationType;
    private String value;

    public ValidationRule() {
    }

    public String getValidationType() {
        return validationType;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValidationRule{" +
                "validationType='" + validationType + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
