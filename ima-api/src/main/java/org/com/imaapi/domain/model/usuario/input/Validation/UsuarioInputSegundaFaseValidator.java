package org.com.imaapi.domain.model.usuario.input.Validation;

import org.com.imaapi.domain.model.usuario.input.UsuarioInputSegundaFase;
import java.time.LocalDate;

public class UsuarioInputSegundaFaseValidator {
    public static void validar(UsuarioInputSegundaFase input) {
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