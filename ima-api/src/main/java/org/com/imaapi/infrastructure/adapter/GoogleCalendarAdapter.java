package org.com.imaapi.infrastructure.adapter;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson  .GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import org.com.imaapi.application.dto.evento.EventoDto;
import org.com.imaapi.domain.gateway.CalendarGateway;
import org.com.imaapi.domain.gateway.OauthTokenGateway;
import org.com.imaapi.infrastructure.mapper.DateTimeMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class GoogleCalendarAdapter implements CalendarGateway {

    private static final String NOME_CALENDARIO = "IMA";
    private static final String TIMEZONE_CALENDARIO = "America/Sao_Paulo";
    private static final String NOME_APLICACAO = "IMA API";
    private static final String DESCRICAO_CALENDARIO = "Eventos IMA";

    private static final Set<String> ESCOPO_CALENDAR = Set.of(
            "https://www.googleapis.com/auth/calendar",
            "https://www.googleapis.com/auth/calendar.events"
    );

    private final OauthTokenGateway oauthTokenGateway;

    public GoogleCalendarAdapter(OauthTokenGateway oauthTokenGateway) {
        this.oauthTokenGateway = oauthTokenGateway;
    }

    // ========== Métodos do CalendarGateway (Interface do Domínio) ==========

    @Override
    public void criarEventoParaUsuario(EventoDto eventoDto, String username, String redirectUri) {
        try {
            oauthTokenGateway.validarEscopos(ESCOPO_CALENDAR, username);

            Calendar service = construirCalendarService(username);

            String idCalendario = buscarOuCriarCalendario(service);

            Event evento = criarEvento(eventoDto);
            inserirEvento(service, idCalendario, evento);

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Erro ao criar evento para usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public String criarEventoComMeetParaUsuario(EventoDto eventoDto, String username, String redirectUri) {
        try {
            oauthTokenGateway.validarEscopos(ESCOPO_CALENDAR, username);

            Calendar service = construirCalendarService(username);

            String idCalendario = buscarOuCriarCalendario(service);

            Event evento = criarEventoComMeet(eventoDto);
            Event eventoInserido = inserirEventoComMeet(service, idCalendario, evento);

            return extrairLinkMeet(eventoInserido);

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Erro ao criar evento com Meet: " + e.getMessage(), e);
        }
    }

    private Event criarEvento(EventoDto eventoDto) {
        DateTime dateTimeInicio = DateTimeMapper.toGoogleDateTime(eventoDto.getInicio());
        DateTime dateTimeFim = DateTimeMapper.toGoogleDateTime(eventoDto.getFim());

        Event event = new Event()
                .setSummary(eventoDto.getTitulo())
                .setDescription(eventoDto.getDescricao());

        EventDateTime start = new EventDateTime()
                .setDateTime(dateTimeInicio)
                .setTimeZone(TIMEZONE_CALENDARIO);

        EventDateTime end = new EventDateTime()
                .setDateTime(dateTimeFim)
                .setTimeZone(TIMEZONE_CALENDARIO);

        event.setStart(start);
        event.setEnd(end);

        return event;
    }

    private Event criarEventoComMeet(EventoDto eventoDto) {
        Event evento = criarEvento(eventoDto);

        CreateConferenceRequest createConferenceRequest = new CreateConferenceRequest()
                .setRequestId(UUID.randomUUID().toString())
                .setConferenceSolutionKey(new ConferenceSolutionKey().setType("hangoutsMeet"));

        ConferenceData conferenceData = new ConferenceData()
                .setCreateRequest(createConferenceRequest);

        evento.setConferenceData(conferenceData);

        return evento;
    }

    private String buscarOuCriarCalendario(Calendar service) throws IOException {
        Optional<String> idCalendario = buscarCalendario(service);
        return idCalendario.orElseGet(() -> criarCalendario(service));
    }

    private Optional<String> buscarCalendario(Calendar service) throws IOException {
        String paginaToken = null;
        do {
            CalendarList calendarList = service.calendarList().list().setPageToken(paginaToken).execute();
            for (CalendarListEntry calendario : calendarList.getItems()) {
                if (NOME_CALENDARIO.equals(calendario.getSummary())) {
                    return Optional.of(calendario.getId());
                }
            }
            paginaToken = calendarList.getNextPageToken();
        } while (paginaToken != null);

        return Optional.empty();
    }

    private String criarCalendario(Calendar service) {
        try {
            com.google.api.services.calendar.model.Calendar novoCalendario =
                    new com.google.api.services.calendar.model.Calendar();

            novoCalendario.setSummary(NOME_CALENDARIO);
            novoCalendario.setTimeZone(TIMEZONE_CALENDARIO);
            novoCalendario.setDescription(DESCRICAO_CALENDARIO);

            com.google.api.services.calendar.model.Calendar calendarioCriado =
                    service.calendars().insert(novoCalendario).execute();
            return calendarioCriado.getId();

        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar calendário: " + e.getMessage(), e);
        }
    }

    private void inserirEvento(Calendar service, String idCalendario, Event evento) throws IOException {
        service.events().insert(idCalendario, evento).execute();
    }

    private Event inserirEventoComMeet(Calendar service, String idCalendario, Event evento) throws IOException {
        return service.events().insert(idCalendario, evento)
                .setConferenceDataVersion(1)
                .execute();
    }

    private String extrairLinkMeet(Event evento) {
        if (evento.getConferenceData() != null &&
                evento.getConferenceData().getEntryPoints() != null) {

            for (EntryPoint entryPoint : evento.getConferenceData().getEntryPoints()) {
                if ("video".equals(entryPoint.getEntryPointType())) {
                    return entryPoint.getUri();
                }
            }
        }
        throw new RuntimeException("Link do Meet não encontrado no evento: " + evento.getId());
    }

    private Calendar construirCalendarService(String username)
            throws GeneralSecurityException, IOException {

        String accessToken = oauthTokenGateway.obterAccessToken(username);


        UserCredentials userCredentials = UserCredentials.newBuilder()
                .setClientId("YOUR_CLIENT_ID")
                .setClientSecret("YOUR_CLIENT_SECRET")
                .setAccessToken(
                        new AccessToken(
                                accessToken,
                                java.util.Date.from(Instant.now().plusSeconds(3600))
                        )
                )
                .build();

        return new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(userCredentials)
        )
                .setApplicationName(NOME_APLICACAO)
                .build();
    }
}
