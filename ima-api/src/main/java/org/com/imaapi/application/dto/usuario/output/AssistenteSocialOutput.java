package org.com.imaapi.application.dto.usuario.output;

import lombok.Data;

@Data
public class AssistenteSocialOutput {
    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String crp;
    private String especialidade;
    private String telefone;
    private String email;
    private String bio;
    private String fotoUrl;
    private EnderecoOutput endereco;
}
