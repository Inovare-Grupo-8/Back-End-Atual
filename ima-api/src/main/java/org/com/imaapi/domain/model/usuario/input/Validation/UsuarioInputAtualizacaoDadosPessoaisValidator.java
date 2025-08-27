package org.com.imaapi.model.usuario.input.Validation;

import org.com.imaapi.model.usuario.input.UsuarioInputAtualizacaoDadosPessoais;

public class UsuarioInputAtualizacaoDadosPessoaisValidator {
    public static void validar(UsuarioInputAtualizacaoDadosPessoais input) {
        if (input.getNome() == null || input.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (input.getSobrenome() == null || input.getSobrenome().trim().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome é obrigatório");
        }
        if (input.getEmail() == null || input.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (input.getSenha() == null || input.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        if (input.getDataNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória");
        }
        if (input.getGenero() == null) {
            throw new IllegalArgumentException("Gênero é obrigatório");
        }
        // Os demais campos podem ser opcionais conforme regra de negócio
    }
}