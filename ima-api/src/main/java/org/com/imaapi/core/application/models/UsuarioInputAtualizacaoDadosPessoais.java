package org.com.imaapi.core.application.models;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioInputAtualizacaoDadosPessoais {
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String senha;
    private LocalDate dataNascimento;
}
