package org.com.imaapi.domain.model.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.com.imaapi.domain.model.enums.ModalidadeConsulta;

@Converter(autoApply = true)
public class ModalidadeConsultaConverter implements AttributeConverter<ModalidadeConsulta, String> {
    @Override
    public String convertToDatabaseColumn(ModalidadeConsulta attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ModalidadeConsulta convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return ModalidadeConsulta.fromValue(dbData);
    }
}
