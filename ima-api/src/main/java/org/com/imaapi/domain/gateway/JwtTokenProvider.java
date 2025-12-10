package org.com.imaapi.domain.gateway;

import java.util.Date;
import java.util.List;

public interface JwtTokenProvider {
    String gerarToken(String username, List<String> authorities);
    boolean validarToken(String usuarioAutenticado, String token);
    String extrairUsernameFromToken(String token);
    Date extrairExpirationDateFromToken(String token);
}
