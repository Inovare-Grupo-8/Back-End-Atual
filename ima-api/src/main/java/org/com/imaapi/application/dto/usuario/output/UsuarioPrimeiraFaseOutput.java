package org.com.imaapi.application.dto.usuario.output;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioPrimeiraFaseOutput {
    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
}
