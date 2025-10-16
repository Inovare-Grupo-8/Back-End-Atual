package org.com.imaapi.domain.model.enums;

public enum ModalidadeConsulta {
    ONLINE("online"),
    PRESENCIAL("presencial");

    private final String value;

    ModalidadeConsulta(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ModalidadeConsulta fromValue(String value) {
        for (ModalidadeConsulta modalidade : values()) {
            if (modalidade.value.equalsIgnoreCase(value)) {
                return modalidade;
            }
        }
        throw new IllegalArgumentException("Modalidade inválida: " + value);
    }
}
