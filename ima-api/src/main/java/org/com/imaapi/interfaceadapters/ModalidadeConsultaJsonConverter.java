package org.com.imaapi.interfaceadapters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.imaapi.domain.model.enums.ModalidadeConsulta;

import java.io.IOException;

public class ModalidadeConsultaJsonConverter {
    public static class Deserializer extends JsonDeserializer<ModalidadeConsulta> {
        @Override
        public ModalidadeConsulta deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String input = node.asText();

            try {
                return ModalidadeConsulta.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ModalidadeConsulta.fromValue(input);
            }
        }
    }

    public static class Serializer {
        public static String toJson(ModalidadeConsulta modalidade) throws JsonProcessingException {
            return new ObjectMapper().writeValueAsString(modalidade.name());
        }
    }
}
