package org.com.imaapi.domain.model.usuario.Validacao.Validation;

import org.com.imaapi.domain.model.usuario.usuarioInputDTO.VoluntarioInputDTO;

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