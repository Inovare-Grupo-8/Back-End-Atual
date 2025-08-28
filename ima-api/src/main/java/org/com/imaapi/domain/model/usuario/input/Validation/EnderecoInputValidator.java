package org.com.imaapi.domain.model.usuario.input.Validation;


import org.com.imaapi.domain.model.usuario.input.EnderecoInput;

public class EnderecoInputValidator {
    public static void validar(EnderecoInput input) {
        if (input.getCep() == null || input.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        if (input.getNumero() == null || input.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Número é obrigatório");
        }
    }
}