package org.com.imaapi.interfaceadapters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.com.imaapi.domain.model.enums.StatusConsulta;

@Converter(autoApply = true)
public class StatusConsultaConverter implements AttributeConverter<StatusConsulta, String> {
    @Override
    public String convertToDatabaseColumn(StatusConsulta attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public StatusConsulta convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusConsulta.fromValue(dbData);
    }
}
