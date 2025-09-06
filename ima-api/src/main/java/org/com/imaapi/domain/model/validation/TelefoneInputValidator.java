package org.com.imaapi.domain.model.validation;

import org.com.imaapi.application.dto.usuario.input.TelefoneInputDTO;

public class TelefoneInputValidator {
    public static void validar(TelefoneInputDTO input) {
        if (input.getDdd() == null || input.getDdd().trim().isEmpty()) {
            throw new IllegalArgumentException("DDD é obrigatório");
        }
        if (input.getPrefixo() == null || input.getPrefixo().trim().isEmpty()) {
            throw new IllegalArgumentException("Prefixo é obrigatório");
        }
        if (input.getSufixo() == null || input.getSufixo().trim().isEmpty()) {
            throw new IllegalArgumentException("Sufixo é obrigatório");
        }
    }
}