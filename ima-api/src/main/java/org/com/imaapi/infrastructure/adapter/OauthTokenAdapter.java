package org.com.imaapi.infrastructure.adapter;

import org.com.imaapi.domain.gateway.OauthTokenGateway;
import org.com.imaapi.infrastructure.exception.MissingScopeException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Component
public class OauthTokenAdapter implements OauthTokenGateway {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final OAuth2AuthorizedClientManager clientManager;
    private static final String CLIENT_REGISTRATION_ID = "google";

    public OauthTokenAdapter(OAuth2AuthorizedClientService authorizedClientService,
                             OAuth2AuthorizedClientManager clientManager) {
        this.authorizedClientService = authorizedClientService;
        this.clientManager = clientManager;
    }

    @Override
    public boolean contemEscoposNecessarios(Set<String> escopos, String username) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                CLIENT_REGISTRATION_ID, username);

        if (client == null || client.getAccessToken() == null) {
            return false;
        }

        OAuth2AccessToken accessToken = client.getAccessToken();

        // Renova o token se estiver expirado
        if (tokenEstaParaExpirar(accessToken)) {
            client = renovarToken(username);
            accessToken = client.getAccessToken();
        }

        Set<String> escoposRecebidos = accessToken.getScopes();
        return escoposRecebidos != null && escoposRecebidos.containsAll(escopos);
    }

    @Override
    public String construirUrlIncremental(Set<String> escoposAdicionais, String username) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                CLIENT_REGISTRATION_ID, username);

        if (client == null) {
            throw new IllegalStateException("Não foi possível obter o cliente autorizado para: "
                    + CLIENT_REGISTRATION_ID);
        }

        ClientRegistration registration = client.getClientRegistration();

        // Gera state e redirectUri automaticamente
        String state = "incremental-" + UUID.randomUUID();
        String redirectUri = "http://localhost:8080/oauth2/googlecallback"; // Pode ser configurável

        return UriComponentsBuilder
                .fromUriString(registration.getProviderDetails().getAuthorizationUri())
                .queryParam("client_id", registration.getClientId())
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(" ", escoposAdicionais))
                .queryParam("include_granted_scopes", "true")
                .queryParam("access_type", "offline")
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    @Override
    public String obterAccessToken(String username) {
        OAuth2AuthorizedClient client = renovarToken(username);
        return client.getAccessToken().getTokenValue();
    }

    @Override
    public void trocarCodePorToken(String code, String username, String redirectUri) {
        OAuth2AuthorizedClient clientAtual = authorizedClientService.loadAuthorizedClient(
                CLIENT_REGISTRATION_ID, username);

        if (clientAtual == null) {
            throw new IllegalStateException("Não foi possível obter o OAuth2AuthorizedClient para "
                    + CLIENT_REGISTRATION_ID);
        }

        ClientRegistration registration = clientAtual.getClientRegistration();
        String state = "incremental-" + UUID.randomUUID();

        OAuth2AuthorizationRequest authRequest = OAuth2AuthorizationRequest.authorizationCode()
                .clientId(registration.getClientId())
                .authorizationUri(registration.getProviderDetails().getAuthorizationUri())
                .redirectUri(redirectUri)
                .state(state)
                .build();

        OAuth2AuthorizationCodeGrantRequest grantRequest = new OAuth2AuthorizationCodeGrantRequest(
                registration,
                new OAuth2AuthorizationExchange(
                        authRequest,
                        OAuth2AuthorizationResponse.success(code)
                                .redirectUri(redirectUri)
                                .state(state)
                                .build()
                ));

        RestClientAuthorizationCodeTokenResponseClient tokenResponseClient =
                new RestClientAuthorizationCodeTokenResponseClient();
        OAuth2AccessTokenResponse tokenResponse = tokenResponseClient.getTokenResponse(grantRequest);

        OAuth2AuthorizedClient clientAtualizado = new OAuth2AuthorizedClient(
                registration,
                username,
                tokenResponse.getAccessToken(),
                tokenResponse.getRefreshToken() != null ?
                        tokenResponse.getRefreshToken() : clientAtual.getRefreshToken()
        );

        // Salva sem Authentication (apenas com username)
        authorizedClientService.saveAuthorizedClient(clientAtualizado, null);
    }

    @Override
    public void validarEscopos(Set<String> escopos, String username) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                CLIENT_REGISTRATION_ID, username);

        if (client == null) {
            throw new IllegalStateException("Cliente OAuth2 não encontrado para o usuário: " + username);
        }

        // Renova o token se estiver expirado
        if (tokenEstaParaExpirar(client.getAccessToken())) {
            client = renovarToken(username);
        }

        Set<String> escoposDoToken = client.getAccessToken().getScopes();

        if (escoposDoToken == null || !escoposDoToken.containsAll(escopos)) {
            String urlIncremental = construirUrlIncremental(escopos, username);

            throw new MissingScopeException(
                    "Usuário não concedeu todos os escopos necessários",
                    escopos,
                    escoposDoToken,
                    urlIncremental
            );
        }
    }

    private boolean tokenEstaParaExpirar(OAuth2AccessToken token) {
        return token.getExpiresAt() == null ||
                token.getExpiresAt().isBefore(Instant.now().minusSeconds(30));
    }

    private OAuth2AuthorizedClient renovarToken(String username) {
        // Cria um Authentication fake apenas com o username para o OAuth2AuthorizedClientManager
        Authentication fakeAuth =
                org.springframework.security.authentication.UsernamePasswordAuthenticationToken
                        .authenticated(username, null, java.util.Collections.emptyList());

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(CLIENT_REGISTRATION_ID)
                .principal(fakeAuth)
                .build();

        OAuth2AuthorizedClient clientAtualizado = clientManager.authorize(authorizeRequest);

        if (clientAtualizado == null) {
            throw new OAuth2AuthenticationException("Falha ao renovar o token de acesso");
        }

        // Salva o cliente atualizado
        authorizedClientService.saveAuthorizedClient(clientAtualizado, fakeAuth);

        return clientAtualizado;
    }
}
