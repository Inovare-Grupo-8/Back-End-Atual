package org.com.imaapi.application.dto.usuario.output;

import lombok.Data;
import org.com.imaapi.domain.model.Endereco;

@Data
public class AssistenteSocialOutputDTO {
    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String crp;
    private String especialidade;
    private String telefone;
    private String email;
    private String bio;
    private String fotoUrl;
    private Endereco endereco;
}
