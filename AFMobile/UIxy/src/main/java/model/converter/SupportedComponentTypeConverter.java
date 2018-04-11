package model.converter;

import model.SupportedComponentType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SupportedComponentTypeConverter implements AttributeConverter<SupportedComponentType, String> {

    public String convertToDatabaseColumn(SupportedComponentType value) {
        if (value == null) {
            return null;
        }

        return value.name();
    }

    public SupportedComponentType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return SupportedComponentType.valueOf(value);
    }
}
