package org.com.imaapi.domain.model.usuario.Validacao.Validation;

public class AssistenteSocialInputValidator {
    public static void validar(AssistenteSocialInput input) {
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
        if (input.getCpf() == null || input.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        if (input.getDataNascimento() == null || input.getDataNascimento().trim().isEmpty()) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória");
        }
        if (input.getGenero() == null || input.getGenero().trim().isEmpty()) {
            throw new IllegalArgumentException("Gênero é obrigatório");
        }
        if (input.getCrp() == null || input.getCrp().trim().isEmpty()) {
            throw new IllegalArgumentException("CRP é obrigatório");
        }

    }
}