package org.com.imaapi.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.usuario.input.AssistenteSocialInput;
import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.useCase.usuario.AtualizarAssistenteSocialUseCase;
import org.com.imaapi.application.useCase.usuario.BuscarAssistenteSocialUseCase;
import org.com.imaapi.application.useCase.usuario.BuscarTodosAssistentesSociaisUseCase;
import org.com.imaapi.application.useCase.usuario.CadastrarAssistenteSocialUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assistentes-sociais")
@RequiredArgsConstructor
public class AssistenteSocialController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssistenteSocialController.class);

    private final CadastrarAssistenteSocialUseCase cadastrarAssistenteSocialUseCase;
    private final BuscarAssistenteSocialUseCase buscarAssistenteSocialUseCase;
    private final BuscarTodosAssistentesSociaisUseCase buscarTodosAssistentesSociaisUseCase;
    private final AtualizarAssistenteSocialUseCase atualizarAssistenteSocialUseCase;

    @PostMapping
    public ResponseEntity<AssistenteSocialOutput> cadastrar(@RequestBody AssistenteSocialInput input) {
        LOGGER.info("Iniciando cadastro de assistente social para email: {}", input.getEmail());
        AssistenteSocialOutput output = cadastrarAssistenteSocialUseCase.executar(input);
        LOGGER.info("Assistente social cadastrado com sucesso para email: {}", input.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping
    public ResponseEntity<List<AssistenteSocialOutput>> buscarTodos() {
        LOGGER.info("Buscando todos os assistentes sociais");
        List<AssistenteSocialOutput> assistentesSociais = buscarTodosAssistentesSociaisUseCase.executar();
        LOGGER.info("Encontrados {} assistentes sociais", assistentesSociais.size());
        return ResponseEntity.ok(assistentesSociais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssistenteSocialOutput> buscarPorId(@PathVariable Integer id) {
        LOGGER.info("Buscando assistente social por ID: {}", id);
        AssistenteSocialOutput output = buscarAssistenteSocialUseCase.executar(id);
        if (output == null) {
            LOGGER.warn("Assistente social não encontrado para o ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        LOGGER.info("Assistente social encontrado com sucesso. ID: {}", id);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/perfil")
    public ResponseEntity<AssistenteSocialOutput> getPerfil(@RequestParam(required = false) Integer userId) {
        // Se userId não for fornecido, usa ID padrão 1 para teste
        Integer userIdFinal = (userId != null) ? userId : 1;
        LOGGER.info("Buscando perfil do assistente social id: {}", userIdFinal);
        AssistenteSocialOutput output = buscarAssistenteSocialUseCase.executar(userIdFinal);
        LOGGER.info("Perfil do assistente social encontrado id: {}", userIdFinal);
        return ResponseEntity.ok(output);
    }

    @PutMapping("/perfil")
    public ResponseEntity<AssistenteSocialOutput> atualizarPerfil(
            @RequestParam(required = false) Integer userId,
            @RequestBody AssistenteSocialInput input) {
        // Se userId não for fornecido, usa ID padrão 1 para teste
        Integer userIdFinal = (userId != null) ? userId : 1;
        LOGGER.info("Atualizando perfil do assistente social id: {}", userIdFinal);
        AssistenteSocialOutput output = atualizarAssistenteSocialUseCase.executar(userIdFinal, input);
        LOGGER.info("Perfil do assistente social atualizado id: {}", userIdFinal);
        return ResponseEntity.ok(output);
    }

    @PatchMapping("/perfil/completo")
    public ResponseEntity<AssistenteSocialOutput> atualizarPerfilCompleto(
            @RequestParam(required = false) Integer userId,
            @RequestBody AssistenteSocialInput input) {
        try {
            // Se userId não for fornecido, usa ID padrão 1 para teste
            Integer userIdFinal = (userId != null) ? userId : 1;
            LOGGER.info("Atualizando perfil completo do assistente social id: {}", userIdFinal);
            AssistenteSocialOutput output = atualizarAssistenteSocialUseCase.executar(userIdFinal, input);
            LOGGER.info("Perfil completo do assistente social atualizado id: {}", userIdFinal);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            LOGGER.error("Erro interno ao atualizar perfil completo do assistente social", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
