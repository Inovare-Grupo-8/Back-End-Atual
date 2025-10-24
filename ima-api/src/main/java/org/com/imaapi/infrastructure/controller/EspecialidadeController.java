package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.especialidade.input.EspecialidadeInput;
import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.application.useCase.especialidade.AtualizarEspecialidadeUseCase;
import org.com.imaapi.application.useCase.especialidade.BuscarEspecialidadePorIdUseCase;
import org.com.imaapi.application.useCase.especialidade.CriarEspecialidadeUseCase;
import org.com.imaapi.application.useCase.especialidade.DeletarEspecialidadeUseCase;
import org.com.imaapi.application.useCase.especialidade.ListarEspecialidadesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/especialidade")
@RequiredArgsConstructor
public class EspecialidadeController {
    private final CriarEspecialidadeUseCase criarEspecialidadeUseCase;
    private final AtualizarEspecialidadeUseCase atualizarEspecialidadeUseCase;
    private final BuscarEspecialidadePorIdUseCase buscarEspecialidadePorIdUseCase;
    private final ListarEspecialidadesUseCase listarEspecialidadesUseCase;
    private final DeletarEspecialidadeUseCase deletarEspecialidadeUseCase;

    @PostMapping
    @Operation(summary = "Criar uma nova especialidade")
    public ResponseEntity<EspecialidadeOutput> criar(@RequestBody EspecialidadeInput especialidadeInput) {
        EspecialidadeOutput especialidade = criarEspecialidadeUseCase.criarEspecialidade(especialidadeInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidade);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma especialidade existente")
    public ResponseEntity<EspecialidadeOutput> atualizar(@PathVariable Integer id, @RequestBody EspecialidadeInput especialidadeInput) {
        EspecialidadeOutput especialidade = atualizarEspecialidadeUseCase.atualizarEspecialidade(id, especialidadeInput);
        return ResponseEntity.ok(especialidade);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma especialidade por ID")
    public ResponseEntity<EspecialidadeOutput> buscarPorId(@PathVariable Integer id) {
        EspecialidadeOutput especialidade = buscarEspecialidadePorIdUseCase.buscarEspecialidadePorId(id);
        return ResponseEntity.ok(especialidade);
    }

    @GetMapping("/paginado")
    @Operation(summary = "Listar especialidades com paginação offset-based")
    public ResponseEntity<List<EspecialidadeOutput>> listarComOffset(
            @Parameter(description = "Número de registros a pular (offset)", example = "0")
            @RequestParam(defaultValue = "0") int offset,
            @Parameter(description = "Número máximo de registros a retornar", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        
        List<EspecialidadeOutput> especialidades = listarEspecialidadesUseCase.listarEspecialidadesComOffset(offset, limit);
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping
    @Operation(summary = "Listar todas as especialidades")
    public ResponseEntity<List<EspecialidadeOutput>> listarTodas() {
        List<EspecialidadeOutput> especialidades = listarEspecialidadesUseCase.listarEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma especialidade")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        deletarEspecialidadeUseCase.deletarEspecialidade(id);
        return ResponseEntity.noContent().build();
    }
}
