package org.com.imaapi.util.service.models;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioDadosPessoaisOutput {
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private String tipo;
}
