package org.com.imaapi.domain.model.validation;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFaseDTO;
import java.time.LocalDate;

public class UsuarioInputSegundaFaseValidator {
    public static void validar(UsuarioInputSegundaFaseDTO input) {
        if (input.getDataNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        }
        if (input.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento inválida");
        }
        if (input.getGenero() == null || input.getGenero().trim().isEmpty()) {
            throw new IllegalArgumentException("O gênero deve ser informado");
        }
        if (input.getTipo() == null) {
            throw new IllegalArgumentException("O tipo de usuário deve ser informado");
        }
        if (input.getEndereco() == null) {
            throw new IllegalArgumentException("Informações do endereço são obrigatórias");
        }
        if (input.getTelefone() == null) {
            throw new IllegalArgumentException("Informações do telefone são obrigatórias");
        }
    }
}