package org.com.imaapi.application.dto.usuario.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public abstract class BaseUsuarioOutputDTO {
    private Integer idUsuario;
    private String nome;
    private String email;
}
