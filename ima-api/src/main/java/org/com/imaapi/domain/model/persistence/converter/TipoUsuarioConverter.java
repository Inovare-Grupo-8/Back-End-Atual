package org.com.imaapi.domain.model.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.com.imaapi.domain.model.enums.TipoUsuario;

@Converter(autoApply = true)
public class TipoUsuarioConverter implements AttributeConverter<TipoUsuario, String> {
    @Override
    public String convertToDatabaseColumn(TipoUsuario attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public TipoUsuario convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoUsuario.fromValue(dbData);
    }
}
