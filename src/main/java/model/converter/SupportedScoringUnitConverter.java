package model.converter;

import service.afclassification.computational.scm.SupportedScoringUnit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SupportedScoringUnitConverter implements AttributeConverter<SupportedScoringUnit, String> {

    public String convertToDatabaseColumn(SupportedScoringUnit value) {
        if (value == null) {
            return null;
        }

        return value.name();
    }

    public SupportedScoringUnit convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return SupportedScoringUnit.valueOf(value);
    }
}
