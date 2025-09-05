package org.com.imaapi.domain.model.usuario.UsuarioOutputDTO;

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
