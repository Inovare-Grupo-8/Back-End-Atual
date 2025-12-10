package org.com.imaapi.infrastructure.config.autenticacao.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioDetalhesOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuarioPorEmailUseCase;
import org.com.imaapi.application.useCase.usuario.CadastrarUsuarioPrimeiraFaseUseCase;
import org.com.imaapi.infrastructure.adapter.AutenticacaoServiceAdapter;
import org.com.imaapi.infrastructure.adapter.GerenciadorTokenJwtAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AutenticacaoSucessHandler implements AuthenticationSuccessHandler {

    private final CadastrarUsuarioPrimeiraFaseUseCase cadastrarUsuario;
    private final BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmail;
    private final GerenciadorTokenJwtAdapter gerenciadorTokenJwtAdapter;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final SecurityContextRepository securityContextRepository;
    private final AutenticacaoServiceAdapter autenticacaoService;

    public AutenticacaoSucessHandler(
            CadastrarUsuarioPrimeiraFaseUseCase cadastrarUsuario,
            BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmail,
            GerenciadorTokenJwtAdapter gerenciadorTokenJwtAdapter,
            OAuth2AuthorizedClientService authorizedClientService,
            SecurityContextRepository securityContextRepository,
            AutenticacaoServiceAdapter autenticacaoService) {
        this.cadastrarUsuario = cadastrarUsuario;
        this.buscarUsuarioPorEmail = buscarUsuarioPorEmail;
        this.gerenciadorTokenJwtAdapter = gerenciadorTokenJwtAdapter;
        this.authorizedClientService = authorizedClientService;
        this.securityContextRepository = securityContextRepository;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User usuarioOauth = oauthToken.getPrincipal();

        String email = usuarioOauth.getAttribute("email");
        Boolean emailVerificado = usuarioOauth.getAttribute("email_verified");

        if (emailVerificado != null && !emailVerificado) {
            throw new ServletException("Email não verificado pelo provedor OAuth2.");
        }

        UsuarioInputPrimeiraFase usuarioInput = new UsuarioInputPrimeiraFase();
        usuarioInput.setNome(usuarioOauth.getAttribute("name"));
        usuarioInput.setEmail(email);
        usuarioInput.setSenha("usuario_oauth2_temporario");

        Optional<UsuarioOutput> usuarioExistente = buscarUsuarioPorEmail.executar(email);

        if (usuarioExistente.isEmpty()) {
            cadastrarUsuario.executar(usuarioInput);
        }

        UsuarioDetalhesOutput usuarioDetalhes = (UsuarioDetalhesOutput) autenticacaoService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                usuarioDetalhes,
                null,
                usuarioDetalhes.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        OAuth2AuthorizedClient authorizedClient =
                authorizedClientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName()
                );

        if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
            authorizedClientService.removeAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName()
            );

            OAuth2AuthorizedClient clientComEmail = new OAuth2AuthorizedClient(
                    authorizedClient.getClientRegistration(),
                    email,
                    authorizedClient.getAccessToken(),
                    authorizedClient.getRefreshToken() != null
                            ? authorizedClient.getRefreshToken()
                            : getRefreshTokenFromDatabase(email, oauthToken.getAuthorizedClientRegistrationId())
            );

            authorizedClientService.saveAuthorizedClient(clientComEmail, authenticationToken);
        }

        List<String> authorities = usuarioDetalhes.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String tokenJwt = gerenciadorTokenJwtAdapter.gerarToken(usuarioDetalhes.getUsername(), authorities);
        String redirectUrl = "http://localhost:3030/" + tokenJwt;

        String originalUrl = (String) request.getSession().getAttribute("ORIGINAL_URL");

        if (originalUrl != null && originalUrl.contains("/calendar")) {
            redirectUrl += "&origin=calendar";
            request.getSession().removeAttribute("ORIGINAL_URL");
        }

        response.sendRedirect(redirectUrl);
    }

    private OAuth2RefreshToken getRefreshTokenFromDatabase(String email, String clientRegistrationId) {
        OAuth2AuthorizedClient clientExistente =
                authorizedClientService.loadAuthorizedClient(clientRegistrationId, email);

        if (clientExistente != null) {
            return clientExistente.getRefreshToken();
        }
        return null;
    }

}
