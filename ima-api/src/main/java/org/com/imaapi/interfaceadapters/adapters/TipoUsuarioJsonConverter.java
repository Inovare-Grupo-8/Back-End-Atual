package org.com.imaapi.interfaceadapters.adapters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.imaapi.domain.model.enums.TipoUsuario; // Certifique-se de importar o enum correto
import java.io.IOException;

public class TipoUsuarioJsonConverter {
    public static class Deserializer extends JsonDeserializer<TipoUsuario> {
        @Override
        public TipoUsuario deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String input = node.asText();

            try {
                return TipoUsuario.valueOf(input.toUpperCase()); // name() do enum
            } catch (IllegalArgumentException e) {
                return TipoUsuario.fromValue(input); // fromValue() do domínio
            }
        }
    }

    // Serializador para JSON
    public static class Serializer {
        public static String toJson(TipoUsuario tipo) throws JsonProcessingException {
            return new ObjectMapper().writeValueAsString(tipo.name()); // name() do enum
        }
    }
}
