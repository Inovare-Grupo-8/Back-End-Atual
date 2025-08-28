package org.com.imaapi.domain.model.usuario.input.Validation;

import org.com.imaapi.domain.model.usuario.input.UsuarioLoginInput;

public class UsuarioLoginInputValidator {
    public static void validar(UsuarioLoginInput input) {
        if (input.getEmail() == null || input.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode estar em branco");
        }
        if (input.getSenha() == null || input.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode estar em branco");
        }
    }
}