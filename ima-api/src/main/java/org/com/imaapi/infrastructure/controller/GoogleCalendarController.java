package org.com.imaapi.infrastructure.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.com.imaapi.application.dto.evento.EventoDto;
import org.com.imaapi.domain.gateway.CalendarGateway;
import org.com.imaapi.infrastructure.exception.MissingScopeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calendar/eventos")
public class GoogleCalendarController {

    private final CalendarGateway calendarGateway;

    public GoogleCalendarController(CalendarGateway calendarGateway) {
        this.calendarGateway = calendarGateway;
    }

    @PostMapping
    public ResponseEntity<?> criarEvento(@RequestBody @Valid EventoDto eventoDto,
                                         Authentication authentication,
                                         HttpServletRequest request) {
        try {
            String redirectUri = construirRedirectUri(request);

            // Delega para o gateway do domínio
            calendarGateway.criarEventoParaUsuario(
                    eventoDto,
                    authentication.getName(),
                    redirectUri
            );

            return ResponseEntity.ok(Collections.singletonMap(
                    "mensagem", "Evento criado com sucesso!"
            ));

        } catch (MissingScopeException e) {
            return tratarMissingScopeException(e, request);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("erro", e.getMessage()));
        }
    }

    @PostMapping("/meet")
    public ResponseEntity<?> criarEventoComMeet(@Valid @RequestBody EventoDto eventoDto,
                                                 Authentication authentication,
                                                 HttpServletRequest request) {
        try {
            String redirectUri = construirRedirectUri(request);

            // Delega para o gateway do domínio
            String linkMeet = calendarGateway.criarEventoComMeetParaUsuario(
                    eventoDto,
                    authentication.getName(),
                    redirectUri
            );

            return ResponseEntity.ok(Collections.singletonMap("linkMeet", linkMeet));

        } catch (MissingScopeException e) {
            return tratarMissingScopeException(e, request);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("erro", e.getMessage()));
        }
    }

    private String construirRedirectUri(HttpServletRequest request) {
        return request.getRequestURL().toString()
                .replace(request.getRequestURI(), "/oauth2/googlecallback");
    }

    private ResponseEntity<?> tratarMissingScopeException(MissingScopeException e,
                                                           HttpServletRequest request) {
        request.getSession().setAttribute("ORIGINAL_URL", request.getRequestURI());

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Autorização adicional necessária");
        resposta.put("redirectUrl", e.getIncrementalAuthUrl());
        resposta.put("escoposFaltantes", String.join(", ", e.getRequiredScopes()));

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(resposta);
    }
}
