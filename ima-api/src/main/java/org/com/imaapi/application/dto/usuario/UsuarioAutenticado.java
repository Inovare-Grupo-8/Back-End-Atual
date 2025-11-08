package org.com.imaapi.application.dto.usuario;

import lombok.Getter;
import lombok.Setter;
import org.com.imaapi.domain.model.enums.TipoUsuario;

import java.util.List;

@Getter
@Setter
public class UsuarioAutenticado {
    String username;
    TipoUsuario tipoUsuario;
    List<String> authorities;

    public UsuarioAutenticado(String username, List<String> list){
        this.username = username;
        this.authorities = List.of(tipoUsuario.getValue());
    }
}
