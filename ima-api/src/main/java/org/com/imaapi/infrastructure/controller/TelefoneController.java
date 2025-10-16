package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.usuario.input.TelefoneInput;
import org.com.imaapi.application.dto.usuario.output.TelefoneOutput;
import org.com.imaapi.application.useCase.telefone.TelefoneUseCase;
import org.com.imaapi.domain.model.Telefone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/telefones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Telefones", description = "Operações relacionadas ao gerenciamento de telefones")
public class TelefoneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelefoneController.class);
    
    private final TelefoneUseCase telefoneUseCase;
    
    @GetMapping("/ficha/{idFicha}")
    @Operation(summary = "Buscar telefones por ficha", description = "Retorna todos os telefones associados a uma ficha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Telefones encontrados"),
        @ApiResponse(responseCode = "404", description = "Nenhum telefone encontrado")
    })
    public ResponseEntity<List<TelefoneOutput>> buscarPorFicha(@PathVariable Integer idFicha) {
        LOGGER.info("Buscando telefones para a ficha com ID: {}", idFicha);
        
        List<Telefone> telefones = telefoneUseCase.buscarPorFicha(idFicha);
        
        if (telefones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<TelefoneOutput> outputs = telefones.stream()
                .map(TelefoneOutput::fromEntity)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(outputs);
    }
    
    @GetMapping("/{idTelefone}")
    @Operation(summary = "Buscar telefone por ID", description = "Retorna um telefone específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Telefone encontrado"),
        @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    public ResponseEntity<TelefoneOutput> buscarPorId(@PathVariable Integer idTelefone) {
        LOGGER.info("Buscando telefone com ID: {}", idTelefone);
        
        Optional<Telefone> telefoneOptional = telefoneUseCase.buscarPorId(idTelefone);
        
        if (telefoneOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        TelefoneOutput output = TelefoneOutput.fromEntity(telefoneOptional.get());
        return ResponseEntity.ok(output);
    }
    
    @PostMapping("/ficha/{idFicha}")
    @Operation(summary = "Cadastrar telefone", description = "Cadastra um novo telefone associado a uma ficha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Ficha não encontrada")
    })
    public ResponseEntity<TelefoneOutput> cadastrar(
            @PathVariable Integer idFicha,
            @RequestBody @Valid TelefoneInput telefoneInput) {
        LOGGER.info("Cadastrando novo telefone para a ficha com ID: {}", idFicha);
        
        Telefone telefone = telefoneUseCase.salvar(idFicha, telefoneInput);
        
        if (telefone == null) {
            return ResponseEntity.notFound().build();
        }
        
        TelefoneOutput output = TelefoneOutput.fromEntity(telefone);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
    
    @PutMapping("/{idTelefone}")
    @Operation(summary = "Atualizar telefone", description = "Atualiza os dados de um telefone existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    public ResponseEntity<TelefoneOutput> atualizar(
            @PathVariable Integer idTelefone,
            @RequestBody @Valid TelefoneInput telefoneInput) {
        LOGGER.info("Atualizando telefone com ID: {}", idTelefone);
        
        Telefone telefone = telefoneUseCase.atualizar(idTelefone, telefoneInput);
        
        if (telefone == null) {
            return ResponseEntity.notFound().build();
        }
        
        TelefoneOutput output = TelefoneOutput.fromEntity(telefone);
        return ResponseEntity.ok(output);
    }
    
    @DeleteMapping("/{idTelefone}")
    @Operation(summary = "Remover telefone", description = "Remove um telefone existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Telefone removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Integer idTelefone) {
        LOGGER.info("Removendo telefone com ID: {}", idTelefone);
        
        boolean removido = telefoneUseCase.remover(idTelefone);
        
        if (!removido) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }
}