package imaapi.controller;

import org.com.imaapi.application.dto.disponibilidade.input.DisponibilidadeInput;
import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;
import org.com.imaapi.application.useCase.disponibilidade.AtualizarDisponibilidadeUseCase;
import org.com.imaapi.application.useCase.disponibilidade.CriarDisponibilidadeUseCase;
import org.com.imaapi.infrastructure.controller.DisponibilidadeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DisponibilidadeControllerTest {

    @InjectMocks
    private DisponibilidadeController disponibilidadeController;

    @Mock
    private CriarDisponibilidadeUseCase criarDisponibilidadeUseCase;

    @Mock
    private AtualizarDisponibilidadeUseCase atualizarDisponibilidadeUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarDisponibilidade_Sucesso() {
        // Arrange
        DisponibilidadeInput input = new DisponibilidadeInput();
        input.setDataHorario(LocalDateTime.now());
        input.setUsuarioId(1);

        DisponibilidadeOutput output = new DisponibilidadeOutput();
        output.setId(1);
        output.setDataHorario(input.getDataHorario());
        output.setVoluntarioId(1);
        output.setVoluntarioNome("João Silva");
        output.setCriadoEm(LocalDateTime.now());
        output.setAtualizadoEm(LocalDateTime.now());

        when(criarDisponibilidadeUseCase.criarDisponibilidade(any(DisponibilidadeInput.class))).thenReturn(output);

        // Act
        ResponseEntity<DisponibilidadeOutput> response = disponibilidadeController.criarDisponibilidade(input);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals(1, response.getBody().getVoluntarioId());
        assertEquals("João Silva", response.getBody().getVoluntarioNome());
        verify(criarDisponibilidadeUseCase, times(1)).criarDisponibilidade(any(DisponibilidadeInput.class));
    }

    @Test
    public void testAtualizarDisponibilidade_Sucesso() {
        // Arrange
        Integer id = 1;
        DisponibilidadeInput input = new DisponibilidadeInput();
        input.setDataHorario(LocalDateTime.now());
        input.setUsuarioId(1);

        DisponibilidadeOutput output = new DisponibilidadeOutput();
        output.setId(1);
        output.setDataHorario(input.getDataHorario());
        output.setVoluntarioId(1);
        output.setVoluntarioNome("João Silva");
        output.setCriadoEm(LocalDateTime.now().minusDays(1));
        output.setAtualizadoEm(LocalDateTime.now());

        when(atualizarDisponibilidadeUseCase.atualizarDisponibilidade(eq(id), any(DisponibilidadeInput.class))).thenReturn(output);

        // Act
        ResponseEntity<DisponibilidadeOutput> response = disponibilidadeController.atualizarDisponibilidade(id, input);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals(1, response.getBody().getVoluntarioId());
        assertEquals("João Silva", response.getBody().getVoluntarioNome());
        verify(atualizarDisponibilidadeUseCase, times(1)).atualizarDisponibilidade(eq(id), any(DisponibilidadeInput.class));
    }
}