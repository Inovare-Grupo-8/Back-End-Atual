package org.com.imaapi.domain.model.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.com.imaapi.domain.model.enums.Genero;

@Converter(autoApply = true)
public class GeneroConverter implements AttributeConverter<Genero, String> {
    @Override
    public String convertToDatabaseColumn(Genero attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public Genero convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Genero.fromValue(dbData);
    }
}
