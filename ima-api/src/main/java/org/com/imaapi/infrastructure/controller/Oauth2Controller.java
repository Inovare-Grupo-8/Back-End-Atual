package org.com.imaapi.infrastructure.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.com.imaapi.domain.gateway.OauthTokenGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Controller
@RequestMapping("/api/oauth2")
public class Oauth2Controller {

    private static final Logger logger = LoggerFactory.getLogger(Oauth2Controller.class);
    private static final String OAUTH2_STATE_ATTR = "OAUTH2_STATE";
    private static final String ORIGINAL_URL_ATTR = "ORIGINAL_URL";
    private static final String OAUTH2_CALLBACK_PATH = "/oauth2/googlecallback";

    private final OauthTokenGateway oauthTokenGateway;

    public Oauth2Controller(OauthTokenGateway oauthTokenGateway) {
        this.oauthTokenGateway = oauthTokenGateway;
    }

    @GetMapping("/authorize/calendar")
    public void authorizeCalendar(Authentication authentication,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(required = false) String returnUrl) throws IOException {

        String username = authentication.getName();

        // Escopos adicionais necessários para o Google Calendar
        Set<String> escoposAdicionais = Set.of(
                "https://www.googleapis.com/auth/calendar.app.created",
                "https://www.googleapis.com/auth/calendar.calendarlist"
        );

        // Determina URL de retorno após autorização
        String originalUrl = returnUrl != null ? returnUrl : request.getHeader("Referer");
        if (originalUrl == null) {
            originalUrl = "/";
        }

        // Salva URL original na sessão para redirecionar após callback
        request.getSession().setAttribute(ORIGINAL_URL_ATTR, originalUrl);
        logger.info("Iniciando autorização OAuth2 para usuário: {} - ReturnUrl: {}", username, originalUrl);

        // Constrói URL de autorização incremental via gateway
        String urlAutorizacao = oauthTokenGateway.construirUrlIncremental(escoposAdicionais, username);

        // Redireciona para página de consentimento do Google
        response.sendRedirect(urlAutorizacao);
    }

    @GetMapping("/googlecallback")
    public ResponseEntity<?> callback(@RequestParam String code,
                                      @RequestParam(required = false) String state,
                                      HttpServletRequest request,
                                      Authentication authentication) {

        String username = authentication.getName();
        logger.info("Callback OAuth2 recebido para usuário: {}", username);

        String redirectUri = construirCallbackUri(request);

        try {
            oauthTokenGateway.trocarCodePorToken(code, username, redirectUri);
            logger.info("Tokens obtidos com sucesso para usuário: {}", username);

        } catch (Exception e) {
            logger.error("Erro ao trocar código por token para usuário: {}", username, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("erro", "Falha ao obter tokens de autorização"));
        }

        String redirectUrl = (String) request.getSession().getAttribute(ORIGINAL_URL_ATTR);
        if (redirectUrl == null) {
            redirectUrl = "/";
        }
        request.getSession().removeAttribute(ORIGINAL_URL_ATTR);

        logger.info("Redirecionando usuário {} para: {}", username, redirectUrl);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", redirectUrl)
                .build();
    }

    private String construirCallbackUri(HttpServletRequest request) {
        return request.getRequestURL().toString()
                .replace(request.getRequestURI(), OAUTH2_CALLBACK_PATH);
    }
}
