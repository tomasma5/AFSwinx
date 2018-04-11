package model.converter;

import model.afclassification.Behavior;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BehaviorConverter implements AttributeConverter<Behavior, String> {

    public String convertToDatabaseColumn(Behavior value) {
        if (value == null) {
            return null;
        }

        return value.name();
    }

    public Behavior convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Behavior.valueOf(value);
    }
}
