package org.com.imaapi.domain.model.validation;

import org.com.imaapi.application.dto.usuario.input.VoluntarioDadosProfissionaisInputDTO;

public class VoluntarioDadosProfissionaisInputValidator {
    public static void validar(VoluntarioDadosProfissionaisInputDTO input) {
        if (input.getFuncao() == null) {
            throw new IllegalArgumentException("Função é obrigatória");
        }
        if (input.getRegistroProfissional() == null || input.getRegistroProfissional().trim().isEmpty()) {
            throw new IllegalArgumentException("Registro profissional é obrigatório");
        }
        if (input.getBiografiaProfissional() == null || input.getBiografiaProfissional().trim().isEmpty()) {
            throw new IllegalArgumentException("Biografia profissional é obrigatória");
        }
        // Validação opcional para especialidade/especialidades, conforme regra de negócio
    }
}