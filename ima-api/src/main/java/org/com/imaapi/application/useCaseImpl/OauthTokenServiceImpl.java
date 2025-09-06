package org.com.imaapi.application.useCaseImpl;

public interface OauthTokenServiceImpl {
    OAuth2AccessToken renovarAccessToken(Authentication authentication);

    void salvarToken(Usuario usuario, OAuth2AccessToken accessToken, OAuth2RefreshToken refreshToken);
}
