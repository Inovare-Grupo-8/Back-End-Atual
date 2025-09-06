package org.com.imaapi.infrastructure.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.imaapi.domain.model.enums.Genero;

import java.io.IOException;

public class GeneroJsonConverter {
    public static class Deserializer extends JsonDeserializer<Genero> {
        @Override
        public Genero deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String input = node.asText();

            try {
                return Genero.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return Genero.fromString(input);
            }
        }
    }

    public static class Serializer {
        public static String toJson(Genero genero) throws JsonProcessingException {
            return new ObjectMapper().writeValueAsString(genero.name());
        }
    }
}
