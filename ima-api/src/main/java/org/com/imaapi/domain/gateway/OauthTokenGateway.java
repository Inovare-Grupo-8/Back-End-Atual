package org.com.imaapi.domain.gateway;

import java.util.Set;

public interface OauthTokenGateway {
    boolean contemEscoposNecessarios(Set<String> escopos, String username);
    String construirUrlIncremental(Set<String> escoposAdicionais, String username);
    String obterAccessToken(String username);
    void trocarCodePorToken(String code, String username, String redirectUri);
    void validarEscopos(Set<String> escopos, String username);
}

