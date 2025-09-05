package org.com.imaapi.domain.model.usuario.UsuarioOutputDTO;

import lombok.Data;
import org.com.imaapi.domain.model.enums.TipoUsuario;

@Data
public class UsuarioListarOutputDTO extends BaseUsuarioOutputDTO {
    private TipoUsuario tipo;
}
