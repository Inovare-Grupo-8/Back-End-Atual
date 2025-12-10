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

@RestController
@RequestMapping("/api/disponibilidade")
@RequiredArgsConstructor
public class DisponibilidadeController {
    
    private final CriarDisponibilidadeUseCase criarDisponibilidadeUseCase;
    private final AtualizarDisponibilidadeUseCase atualizarDisponibilidadeUseCase;
    
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
}
