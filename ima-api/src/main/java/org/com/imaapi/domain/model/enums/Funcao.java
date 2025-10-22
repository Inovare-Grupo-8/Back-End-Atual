package org.com.imaapi.domain.model.enums;

public enum Funcao {
    JURIDICA("Juridica"),
    PSICOLOGIA("Psicologia"),
    PSICOPEDAGOGIA("Psicopedagogia"),
    ASSISTENCIA_SOCIAL("Assistencia Social"),
    CONTABIL("Contabil"),
    FINANCEIRA("Financeira"),
    PEDIATRIA("Pediatria"),
    FISIOTERAPIA("Fisioterapia"),
    QUIROPRAXIA("Quiropraxia"),
    NUTRICAO("Nutricao");

    private final String value;

    Funcao(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String normalizeValue(String value) {
        if (value == null) {
            return null;
        }
        return value.trim()
                .toLowerCase()
                .replace(" ", "_")
                .replace("-", "_")
                .replace(".", "_")
                .replace("á", "a")
                .replace("ã", "a")
                .replace("à", "a")
                .replace("é", "e")
                .replace("ê", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ô", "o")
                .replace("ú", "u")
                .replace("â", "a")
                .replace("ê", "e")
                .replace("î", "i")
                .replace("ô", "o")
                .replace("û", "u")
                .replace("ç", "c");
    }

    public static Funcao fromValue(String value) {
        if (value == null) {
            return null;
        }

        String normalizedInput = normalizeValue(value);

        switch (normalizedInput) {
            case "advogado":
            case "advogada":
                return JURIDICA;
            case "psicologo":
            case "psicologa":
                return PSICOLOGIA;
            case "psicopedagogo":
            case "psicopedagoga":
                return PSICOPEDAGOGIA;
            case "assistente_social":
                return ASSISTENCIA_SOCIAL;
            case "contador":
            case "contadora":
                return CONTABIL;
            case "financeiro":
            case "financeira":
                return FINANCEIRA;
            case "pediatra":
                return PEDIATRIA;
            case "fisioterapeuta":
                return FISIOTERAPIA;
            case "quiropraxista":
                return QUIROPRAXIA;
            case "nutricionista":
                return NUTRICAO;
        }

        for (Funcao funcao : Funcao.values()) {
            String normalizedEnum = normalizeValue(funcao.value);
            String normalizedName = normalizeValue(funcao.name());

            if (normalizedEnum.equals(normalizedInput) ||
                    normalizedName.equals(normalizedInput)) {
                return funcao;
            }
        }
        throw new IllegalArgumentException("Função inválida: " + value);
    }
}
