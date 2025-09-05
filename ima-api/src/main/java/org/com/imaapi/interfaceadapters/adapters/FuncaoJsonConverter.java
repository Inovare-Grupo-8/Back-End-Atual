package org.com.imaapi.interfaceadapters.adapters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.imaapi.domain.model.enums.Funcao;

import java.io.IOException;

public class FuncaoJsonConverter {
    public static class Deserializer extends JsonDeserializer<Funcao> {
        @Override
        public Funcao deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String input = node.asText();

            try {
                return Funcao.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return Funcao.fromValue(input);
            }
        }
    }

    public static class Serializer {
        public static String toJson(Funcao funcao) throws JsonProcessingException {
            return new ObjectMapper().writeValueAsString(funcao.name());
        }
    }
}
