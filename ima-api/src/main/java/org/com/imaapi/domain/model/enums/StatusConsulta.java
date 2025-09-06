package org.com.imaapi.domain.model.enums;

public enum StatusConsulta {
    AGENDADA("Agendada"),
    REALIZADA("Realizada"),
    CANCELADA("Cancelada");

    private final String value;

    StatusConsulta(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StatusConsulta fromValue(String value) {
        for (StatusConsulta status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de consulta inválido: " + value);
    }
}
