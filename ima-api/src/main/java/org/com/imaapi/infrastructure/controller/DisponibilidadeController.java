package org.com.imaapi.infrastructure.controller;

import org.com.imaapi.domain.model.Disponibilidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {
    @Autowired
    private Disponibilidade disponibilidadeService;

    @PostMapping
    public ResponseEntity<Void> criarDisponibilidade(
            @RequestParam Integer usuarioId,
            @RequestBody Disponibilidade disponibilidade) {
        boolean criado = disponibilidadeService.criarDisponibilidade(usuarioId, disponibilidade);
        if (!criado) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).build();
    }

    @PatchMapping
    public ResponseEntity<Void> atualizarDisponibilidade(
            @RequestParam Integer usuarioId,
            @RequestBody Disponibilidade disponibilidade) {
        boolean atualizado = disponibilidadeService.atualizarDisponibilidade(usuarioId, disponibilidade);
        if (!atualizado) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
