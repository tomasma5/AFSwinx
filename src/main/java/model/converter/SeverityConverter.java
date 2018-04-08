package model.converter;

import model.afclassification.Severity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SeverityConverter implements AttributeConverter<Severity, String> {

    public String convertToDatabaseColumn(Severity value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public Severity convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Severity.valueOf(value);
    }
}
