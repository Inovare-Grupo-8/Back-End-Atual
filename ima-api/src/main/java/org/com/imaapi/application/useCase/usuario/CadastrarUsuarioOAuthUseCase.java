package org.com.imaapi.application.useCase.usuario;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CadastrarUsuarioOAuthUseCase {
    void executar(OAuth2User usuario);
}