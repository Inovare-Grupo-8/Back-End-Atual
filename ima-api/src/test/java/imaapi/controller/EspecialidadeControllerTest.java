package imaapi.controller;

import org.com.imaapi.application.dto.especialidade.input.EspecialidadeInput;
import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.application.useCase.especialidade.AtualizarEspecialidadeUseCase;
import org.com.imaapi.application.useCase.especialidade.BuscarEspecialidadePorIdUseCase;
import org.com.imaapi.application.useCase.especialidade.CriarEspecialidadeUseCase;
import org.com.imaapi.application.useCase.especialidade.DeletarEspecialidadeUseCase;
import org.com.imaapi.application.useCase.especialidade.ListarEspecialidadesUseCase;
import org.com.imaapi.infrastructure.controller.EspecialidadeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EspecialidadeControllerTest {

    @InjectMocks
    private EspecialidadeController especialidadeController;

    @Mock
    private CriarEspecialidadeUseCase criarEspecialidadeUseCase;

    @Mock
    private AtualizarEspecialidadeUseCase atualizarEspecialidadeUseCase;

    @Mock
    private BuscarEspecialidadePorIdUseCase buscarEspecialidadePorIdUseCase;

    @Mock
    private ListarEspecialidadesUseCase listarEspecialidadesUseCase;

    @Mock
    private DeletarEspecialidadeUseCase deletarEspecialidadeUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriar_Sucesso() {
        // Arrange
        EspecialidadeInput input = new EspecialidadeInput();
        input.setNome("Psicologia");

        EspecialidadeOutput output = new EspecialidadeOutput();
        output.setId(1);
        output.setNome("Psicologia");

        when(criarEspecialidadeUseCase.criarEspecialidade(any(EspecialidadeInput.class))).thenReturn(output);

        // Act
        ResponseEntity<EspecialidadeOutput> response = especialidadeController.criar(input);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Psicologia", response.getBody().getNome());
        verify(criarEspecialidadeUseCase, times(1)).criarEspecialidade(any(EspecialidadeInput.class));
    }

    @Test
    public void testAtualizar_Sucesso() {
        // Arrange
        Integer id = 1;
        EspecialidadeInput input = new EspecialidadeInput();
        input.setNome("Psicologia Atualizada");

        EspecialidadeOutput output = new EspecialidadeOutput();
        output.setId(1);
        output.setNome("Psicologia Atualizada");

        when(atualizarEspecialidadeUseCase.atualizarEspecialidade(eq(id), any(EspecialidadeInput.class))).thenReturn(output);

        // Act
        ResponseEntity<EspecialidadeOutput> response = especialidadeController.atualizar(id, input);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Psicologia Atualizada", response.getBody().getNome());
        verify(atualizarEspecialidadeUseCase, times(1)).atualizarEspecialidade(eq(id), any(EspecialidadeInput.class));
    }

    @Test
    public void testBuscarPorId_Sucesso() {
        // Arrange
        Integer id = 1;
        EspecialidadeOutput output = new EspecialidadeOutput();
        output.setId(1);
        output.setNome("Psicologia");

        when(buscarEspecialidadePorIdUseCase.buscarEspecialidadePorId(id)).thenReturn(output);

        // Act
        ResponseEntity<EspecialidadeOutput> response = especialidadeController.buscarPorId(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Psicologia", response.getBody().getNome());
        verify(buscarEspecialidadePorIdUseCase, times(1)).buscarEspecialidadePorId(id);
    }

    @Test
    public void testListarTodas_Sucesso() {
        // Arrange
        EspecialidadeOutput especialidade1 = new EspecialidadeOutput();
        especialidade1.setId(1);
        especialidade1.setNome("Psicologia");

        EspecialidadeOutput especialidade2 = new EspecialidadeOutput();
        especialidade2.setId(2);
        especialidade2.setNome("Nutrição");

        List<EspecialidadeOutput> especialidades = Arrays.asList(especialidade1, especialidade2);

        when(listarEspecialidadesUseCase.listarEspecialidades()).thenReturn(especialidades);

        // Act
        ResponseEntity<List<EspecialidadeOutput>> response = especialidadeController.listarTodas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Psicologia", response.getBody().get(0).getNome());
        assertEquals("Nutrição", response.getBody().get(1).getNome());
        verify(listarEspecialidadesUseCase, times(1)).listarEspecialidades();
    }

    @Test
    public void testDeletar_Sucesso() {
        // Arrange
        Integer id = 1;
        doNothing().when(deletarEspecialidadeUseCase).deletarEspecialidade(id);

        // Act
        ResponseEntity<Void> response = especialidadeController.deletar(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletarEspecialidadeUseCase, times(1)).deletarEspecialidade(id);
    }
}