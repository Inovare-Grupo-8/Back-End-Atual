package org.com.imaapi.application.dto.usuario.output;

import lombok.Data;
import org.com.imaapi.domain.model.enums.TipoUsuario;

@Data
public class UsuarioListarOutputDTO extends BaseUsuarioOutputDTO {
    private TipoUsuario tipo;
}
