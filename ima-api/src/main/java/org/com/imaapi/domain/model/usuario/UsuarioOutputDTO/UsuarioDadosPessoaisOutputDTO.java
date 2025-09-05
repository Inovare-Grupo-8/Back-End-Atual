package org.com.imaapi.domain.model.usuario.UsuarioOutputDTO;

import lombok.Data;
import org.com.imaapi.domain.model.enums.Genero;

import java.time.LocalDate;

@Data
public class UsuarioDadosPessoaisOutputDTO {
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private Genero genero;
    private String tipo;    // Campos específicos para assistente social
    private String crp;
    private String bio;
    private String especialidade;
    private String fotoUrl;
}