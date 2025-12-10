package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.disponibilidade.input.DisponibilidadeInput;
import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;
import org.com.imaapi.application.useCase.disponibilidade.AtualizarDisponibilidadeUseCase;
import org.com.imaapi.application.useCase.disponibilidade.CriarDisponibilidadeUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.com.imaapi.application.useCase.disponibilidade.ListarDisponibilidadesUseCase;
import org.com.imaapi.application.useCase.disponibilidade.DeletarDisponibilidadeUseCase;

@RestController
@RequestMapping("/disponibilidade")
@RequiredArgsConstructor
public class DisponibilidadeController {
    
    private final CriarDisponibilidadeUseCase criarDisponibilidadeUseCase;
    private final AtualizarDisponibilidadeUseCase atualizarDisponibilidadeUseCase;
    private final ListarDisponibilidadesUseCase listarDisponibilidadesUseCase;
    private final DeletarDisponibilidadeUseCase deletarDisponibilidadeUseCase;
    
    @PostMapping
    @Operation(summary = "Criar uma nova disponibilidade")
    public ResponseEntity<DisponibilidadeOutput> criarDisponibilidade(@RequestBody DisponibilidadeInput disponibilidadeInput) {
        DisponibilidadeOutput disponibilidade = criarDisponibilidadeUseCase.criarDisponibilidade(disponibilidadeInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(disponibilidade);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar uma disponibilidade existente")
    public ResponseEntity<DisponibilidadeOutput> atualizarDisponibilidade(
            @PathVariable Integer id,
            @RequestBody DisponibilidadeInput disponibilidadeInput) {
        DisponibilidadeOutput disponibilidade = atualizarDisponibilidadeUseCase.atualizarDisponibilidade(id, disponibilidadeInput);
        return ResponseEntity.ok(disponibilidade);
    }

    @GetMapping
    @Operation(summary = "Listar todas as disponibilidades")
    public ResponseEntity<List<DisponibilidadeOutput>> listarTodas() {
        List<DisponibilidadeOutput> lista = listarDisponibilidadesUseCase.listarDisponibilidades();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/voluntario/{voluntarioId}")
    @Operation(summary = "Listar disponibilidades por voluntário")
    public ResponseEntity<List<DisponibilidadeOutput>> listarPorVoluntario(@PathVariable Integer voluntarioId) {
        List<DisponibilidadeOutput> lista = listarDisponibilidadesUseCase.listarDisponibilidadesPorVoluntario(voluntarioId);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar disponibilidade por ID")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        deletarDisponibilidadeUseCase.deletarDisponibilidade(id);
        return ResponseEntity.ok().build();
    }
}
