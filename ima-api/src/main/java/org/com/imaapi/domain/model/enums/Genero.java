package org.com.imaapi.domain.model.enums;

public enum Genero {
    MASCULINO("MASCULINO"),
    FEMININO("FEMININO"),
    OUTRO("OUTRO");

    private final String value;

    Genero(String value) {
        this.value = value;
    }

    public static Genero fromString(String input) {
        return switch (input.toUpperCase()) {
            case "MASCULINO" -> MASCULINO;
            case "FEMININO" -> FEMININO;
            case "OUTRO" -> OUTRO;
            default -> throw new IllegalArgumentException("Gênero inválido: " + input);
        };
    }

    public String getValue() {
        return value;
    }

    public static Genero fromValue(String value) {
        for (Genero genero : values()) {
            if (genero.value.equalsIgnoreCase(value)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Gênero inválido: " + value);
    }
}
