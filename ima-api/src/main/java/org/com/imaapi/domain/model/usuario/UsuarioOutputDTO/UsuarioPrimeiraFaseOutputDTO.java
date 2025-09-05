package org.com.imaapi.domain.model.usuario.UsuarioOutputDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioPrimeiraFaseOutputDTO {
    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
}
