package org.com.imaapi.domain.model.usuario.Validacao.Validation;


import org.com.imaapi.domain.model.usuario.usuarioInputDTO.EnderecoInputDTO;

public class EnderecoInputValidator {
    public static void validar(EnderecoInputDTO input) {
        if (input.getCep() == null || input.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        if (input.getNumero() == null || input.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Número é obrigatório");
        }
    }
}