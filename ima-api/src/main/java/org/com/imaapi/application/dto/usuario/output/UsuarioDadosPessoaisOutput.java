package org.com.imaapi.application.dto.usuario.output;

import lombok.Data;
import org.com.imaapi.domain.model.enums.Genero;

import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioDadosPessoaisOutput {
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
    private List<String> especialidades;
    private String funcao;
    private String registroProfissional;
    private String biografiaProfissional;
    private String fotoUrl;
}