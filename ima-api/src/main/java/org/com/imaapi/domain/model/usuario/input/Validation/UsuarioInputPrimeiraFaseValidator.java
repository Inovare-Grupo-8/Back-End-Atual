package org.com.imaapi.domain.model.usuario.input.Validation;

import org.com.imaapi.domain.model.usuario.input.UsuarioInputPrimeiraFase;

public class UsuarioInputPrimeiraFaseValidator {
    public static void validar(UsuarioInputPrimeiraFase input) {
        if (input.getNome() == null || input.getNome().trim().isEmpty() || input.getNome().length() < 3 || input.getNome().length() > 50) {
            throw new IllegalArgumentException("Nome não pode estar em branco e deve ter entre 3 e 50 caracteres");
        }
        if (input.getSobrenome() == null || input.getSobrenome().trim().isEmpty() || input.getSobrenome().length() < 3 || input.getSobrenome().length() > 50) {
            throw new IllegalArgumentException("Sobrenome não pode estar em branco e deve ter entre 3 e 50 caracteres");
        }
        if (input.getEmail() == null || !input.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (input.getCpf() == null || !input.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos");
        }
        if (input.getSenha() == null || input.getSenha().trim().isEmpty() || !input.getSenha().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres, incluindo letras, números e um caractere especial");
        }
    }
}