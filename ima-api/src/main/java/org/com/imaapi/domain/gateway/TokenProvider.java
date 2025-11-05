package org.com.imaapi.domain.gateway;

import org.com.imaapi.application.dto.usuario.UsuarioAutenticado;

import java.util.Date;
import java.util.List;

public interface TokenProvider {
    String gerarToken(String username, List<String> authorities);
    boolean validarToken(String usuarioAutenticado, String token);
    String extrairUsernameFromToken(String token);
    Date extrairExpirationDateFromToken(String token);
}
