package org.com.imaapi.domain.model.validation;

import org.com.imaapi.application.dto.usuario.input.DevTokenInput;

public class DevTokenInputValidator {
    public static void validar(DevTokenInput input) {
        if (input.getEmail() == null || input.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (input.getNome() == null || input.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (input.getAuthorities() == null || input.getAuthorities().isEmpty()) {
            throw new IllegalArgumentException("Authorities é obrigatório");
        }
        if (input.getValidityInSeconds() == null || input.getValidityInSeconds() <= 0) {
            throw new IllegalArgumentException("validityInSeconds deve ser maior que zero");
        }
    }
}