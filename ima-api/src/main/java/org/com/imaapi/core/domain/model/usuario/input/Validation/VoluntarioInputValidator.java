package org.com.imaapi.model.usuario.input.Validation;

import org.com.imaapi.model.usuario.input.VoluntarioInput;

public class VoluntarioInputValidator {
    public static void validar(VoluntarioInput input) {
        if (input.getFuncao() == null) {
            throw new IllegalArgumentException("Função é obrigatória");
        }
        if (input.getFkUsuario() == null) {
            throw new IllegalArgumentException("fkUsuario é obrigatório");
        }
    }
}