package org.com.imaapi.model.usuario.input.Validation;

import org.com.imaapi.model.usuario.input.TelefoneInput;

public class TelefoneInputValidator {
    public static void validar(TelefoneInput input) {
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