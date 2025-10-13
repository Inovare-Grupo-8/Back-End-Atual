package org.com.imaapi.domain.model.enums;

public enum Periodo {
    DIA ("dia"),
    SEMANA ("semana"),
    MES ("mês"),
    ATUAL ("atual");

    private final String periodo;

    Periodo(String periodo) {
        this.periodo = periodo;
    }

    public static Periodo fromString(String value) {
        try {
            return Periodo.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Período inválido: " + value);
        }
    }

    public String getPeriodo() {
        return periodo;
    }
}
