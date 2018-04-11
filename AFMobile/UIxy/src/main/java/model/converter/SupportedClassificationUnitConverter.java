package model.converter;

import service.afclassification.computational.ccm.SupportedClassificationUnit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SupportedClassificationUnitConverter implements AttributeConverter<SupportedClassificationUnit, String> {

    public String convertToDatabaseColumn(SupportedClassificationUnit value) {
        if (value == null) {
            return null;
        }

        return value.name();
    }

    public SupportedClassificationUnit convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return SupportedClassificationUnit.valueOf(value);
    }
}
