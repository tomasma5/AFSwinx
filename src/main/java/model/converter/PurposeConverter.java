package model.converter;

import model.afclassification.Purpose;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PurposeConverter implements AttributeConverter<Purpose, String> {

    public String convertToDatabaseColumn(Purpose value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public Purpose convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Purpose.valueOf(value);
    }
}
