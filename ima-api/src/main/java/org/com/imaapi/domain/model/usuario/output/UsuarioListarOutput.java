package org.com.imaapi.domain.model.usuario.output;

import lombok.Data;
import org.com.imaapi.domain.model.enums.TipoUsuario;

@Data
public class UsuarioListarOutput extends BaseUsuarioOutput {
    private TipoUsuario tipo;
}
