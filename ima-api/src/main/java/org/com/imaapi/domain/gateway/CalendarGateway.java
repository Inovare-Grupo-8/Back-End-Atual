package org.com.imaapi.domain.gateway;

import org.com.imaapi.application.dto.evento.EventoDto;

public interface CalendarGateway {
    void criarEventoParaUsuario(EventoDto eventoDto, String username, String redirectUri);
    String criarEventoComMeetParaUsuario(EventoDto eventoDto, String username, String redirectUri);
}

