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

        // Primeiro, tenta encontrar uma correspondência exata
        for (Funcao funcao : Funcao.values()) {
            String normalizedEnum = normalizeValue(funcao.value);
            String normalizedName = normalizeValue(funcao.name());

            if (normalizedEnum.equals(normalizedInput) ||
                    normalizedName.equals(normalizedInput)) {
                return funcao;
            }
        }

        // Se não encontrou correspondência exata, tenta mapeamentos específicos
        // para adaptar valores comuns do banco de dados
        switch (normalizedInput) {
            case "psicologa":
            case "psicologo":
                return PSICOLOGIA;
            case "advogada":
            case "advogado":
                return JURIDICA;
            case "assistente_social":
                return ASSISTENCIA_SOCIAL;
            case "contadora":
            case "contador":
                return CONTABIL;
            case "nutricionista":
                return NUTRICAO;
            case "fisioterapeuta":
                return FISIOTERAPIA;
            case "pediatra":
                return PEDIATRIA;
            case "psicopedagoga":
            case "psicopedagogo":
                return PSICOPEDAGOGIA;
            case "quiroprata":
            case "quiropraxista":
                return QUIROPRAXIA;
            default:
                // Se ainda não encontrou, tenta uma busca parcial
                for (Funcao funcao : Funcao.values()) {
                    String normalizedEnum = normalizeValue(funcao.value);
                    String normalizedName = normalizeValue(funcao.name());
                    
                    // Verifica se o input contém parte do nome do enum ou vice-versa
                    if (normalizedInput.contains(normalizedEnum) || 
                        normalizedEnum.contains(normalizedInput) ||
                        normalizedInput.contains(normalizedName) || 
                        normalizedName.contains(normalizedInput)) {
                        return funcao;
                    }
                }
                
                // Se nada funcionou, lança exceção
                throw new IllegalArgumentException("Função inválida: " + value);
        }
    }
}
