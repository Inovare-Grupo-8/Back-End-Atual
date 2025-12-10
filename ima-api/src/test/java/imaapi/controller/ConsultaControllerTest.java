package imaapi.controller;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.application.useCase.consulta.*;
import org.com.imaapi.infrastructure.controller.ConsultaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ConsultaControllerTest {

    @InjectMocks
    private ConsultaController consultaController;

    @Mock
    private CriarConsultaUseCase criarConsultaUseCase;

    @Mock
    private BuscarConsultasUsuarioLogadoUseCase buscarConsultasUsuarioLogadoUseCase;

    @Mock
    private BuscarConsultaPorIdUseCase buscarConsultaPorIdUseCase;

    @Mock
    private CancelarConsultaUseCase cancelarConsultaUseCase;

    @Mock
    private RemarcarConsultaUseCase remarcarConsultaUseCase;

    @Mock
    private BuscarHistoricoConsultasUseCase buscarHistoricoConsultasUseCase;

    @Mock
    private BuscarProximasConsultasUseCase buscarProximasConsultasUseCase;

    @Mock
    private BuscarTodasConsultasUseCase buscarTodasConsultasUseCase;

    @Mock
    private BuscarHorariosDisponiveisUseCase buscarHorariosDisponiveisUseCase;

    @Mock
    private BuscarAvaliacoesFeedbackUseCase buscarAvaliacoesFeedbackUseCase;

    @Mock
    private AdicionarFeedbackConsultaUseCase adicionarFeedbackConsultaUseCase;

    @Mock
    private AdicionarAvaliacaoConsultaUseCase adicionarAvaliacaoConsultaUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHorariosDisponiveis() {
        LocalDate data = LocalDate.of(2025, 10, 16);
        Integer voluntarioId = 1;
        List<LocalDateTime> horarios = List.of(
                LocalDateTime.of(2025, 10, 16, 9, 0),
                LocalDateTime.of(2025, 10, 16, 10, 0)
        );
        when(buscarHorariosDisponiveisUseCase.buscarHorariosDisponiveis(data, voluntarioId))
                .thenReturn(horarios);

        ResponseEntity<Map<String, Object>> response = consultaController.getHorariosDisponiveis(data, voluntarioId, null);
        assertEquals(200, response.getStatusCode().value());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(data, body.get("data"));
        assertEquals(voluntarioId, body.get("idVoluntario"));
        assertEquals(horarios, body.get("horarios"));
    }

    @Test
    public void testGetTodasConsultas() {
        ConsultaOutput c1 = new ConsultaOutput();
        c1.setIdConsulta(1);
        ConsultaOutput c2 = new ConsultaOutput();
        c2.setIdConsulta(2);
        when(buscarTodasConsultasUseCase.buscarTodasConsultas()).thenReturn(List.of(c1, c2));

        ResponseEntity<List<ConsultaOutput>> response = consultaController.getTodasConsultas();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testListarProximasConsultasComDefaultUser() {
        ConsultaOutput c1 = new ConsultaOutput();
        c1.setIdConsulta(1);
        when(buscarProximasConsultasUseCase.buscarProximasConsultas("assistido", null))
                .thenReturn(List.of(c1));

        ResponseEntity<List<ConsultaOutput>> response = consultaController.listarProximasConsultas("assistido", null);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testListarProximasConsultasComUserId() {
        ConsultaOutput c1 = new ConsultaOutput();
        c1.setIdConsulta(1);
        when(buscarProximasConsultasUseCase.buscarProximasConsultas("voluntario", 2))
                .thenReturn(List.of(c1));

        ResponseEntity<List<ConsultaOutput>> response = consultaController.listarProximasConsultas("voluntario", 2);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetAvaliacoesFeedbackDefaultUser() {
        when(buscarAvaliacoesFeedbackUseCase.buscarAvaliacoesFeedback("assistido"))
                .thenReturn(Map.of("feedbacks", List.of(), "avaliacoes", List.of()));

        ResponseEntity<Map<String, Object>> response = consultaController.getAvaliacoesFeedback("assistido");
        assertEquals(200, response.getStatusCode().value());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.containsKey("feedbacks"));
        assertTrue(body.containsKey("avaliacoes"));
    }
}