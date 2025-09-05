package imaapi.controller;

import org.com.imaapi.controller.ConsultaController;
import org.com.imaapi.application.Usecase.Consulta.dto.ConsultaRequestDTO;
import org.com.imaapi.application.Usecase.Consulta.output.ConsultaOutput;
import org.com.imaapi.util.service.ConsultaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ConsultaControllerTest {

    @InjectMocks
    private ConsultaController consultaController;

    @Mock
    private ConsultaService consultaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarEventoComSucesso() {
        ConsultaRequestDTO consultaInput = new ConsultaRequestDTO();
        ConsultaOutput consultaOutput = new ConsultaOutput();
        ResponseEntity<ConsultaOutput> expectedResponse = ResponseEntity.ok(consultaOutput);
        Mockito.when(consultaService.criarEvento(any(ConsultaRequestDTO.class))).thenReturn(expectedResponse);
        ResponseEntity<ConsultaOutput> response = consultaController.criarEvento(consultaInput);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testCriarEventoComErroValidacao() {
        ConsultaRequestDTO consultaInput = new ConsultaRequestDTO();
        Mockito.when(consultaService.criarEvento(any(ConsultaRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Erro de validação"));
        try {
            consultaController.criarEvento(consultaInput);
        } catch (IllegalArgumentException e) {
            assertEquals("Erro de validação", e.getMessage());
        }
    }

    @Test
    public void testCriarEventoRetornaNull() {
        ConsultaRequestDTO consultaInput = new ConsultaRequestDTO();
        Mockito.when(consultaService.criarEvento(any(ConsultaRequestDTO.class))).thenReturn(null);
        ResponseEntity<ConsultaOutput> response = consultaController.criarEvento(consultaInput);
        assertEquals(null, response);
    }

    @Test
    public void testCriarEventoComErroInterno() {
        ConsultaRequestDTO consultaInput = new ConsultaRequestDTO();
        Mockito.when(consultaService.criarEvento(any(ConsultaRequestDTO.class)))
                .thenThrow(new RuntimeException("Erro interno"));
        try {
            consultaController.criarEvento(consultaInput);
        } catch (RuntimeException e) {
            assertEquals("Erro interno", e.getMessage());
        }
    }

    @Test
    public void testCriarEventoComRespostaNaoEsperada() {
        ConsultaRequestDTO consultaInput = new ConsultaRequestDTO();
        ResponseEntity<ConsultaOutput> unexpectedResponse = ResponseEntity.badRequest().build();
        Mockito.when(consultaService.criarEvento(any(ConsultaRequestDTO.class))).thenReturn(unexpectedResponse);
        ResponseEntity<ConsultaOutput> response = consultaController.criarEvento(consultaInput);
        assertEquals(unexpectedResponse, response);
    }

    @Test
    public void testCriarEventoComInputNulo() {
        try {
            consultaController.criarEvento(null);
        } catch (IllegalArgumentException e) {
            assertEquals("ConsultaInput não pode ser nulo", e.getMessage());
        }
    }
}