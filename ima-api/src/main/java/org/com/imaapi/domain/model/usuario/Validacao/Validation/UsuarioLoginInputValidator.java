package org.com.imaapi.domain.model.usuario.Validacao.Validation;

import org.com.imaapi.domain.model.usuario.usuarioInputDTO.UsuarioLoginInputDTO;

public class UsuarioLoginInputValidator {
    public static void validar(UsuarioLoginInputDTO input) {
        if (input.getEmail() == null || input.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode estar em branco");
        }
        if (input.getSenha() == null || input.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode estar em branco");
        }
    }
}