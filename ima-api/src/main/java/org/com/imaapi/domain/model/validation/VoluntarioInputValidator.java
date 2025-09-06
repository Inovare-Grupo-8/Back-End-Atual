package org.com.imaapi.domain.model.validation;

import org.com.imaapi.application.dto.usuario.input.VoluntarioInputDTO;

public class VoluntarioInputValidator {
    public static void validar(VoluntarioInputDTO input) {
        if (input.getFuncao() == null) {
            throw new IllegalArgumentException("Função é obrigatória");
        }
        if (input.getFkUsuario() == null) {
            throw new IllegalArgumentException("fkUsuario é obrigatório");
        }
    }
}