package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.usuario.input.EnderecoInput;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.useCase.perfil.AtualizarEnderecoUseCase;
import org.com.imaapi.application.useCase.perfil.BuscarEnderecoUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Endereços", description = "Operações relacionadas ao gerenciamento de endereços")
public class EnderecoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnderecoController.class);
    
    private final BuscarEnderecoUseCase buscarEnderecoUseCase;
    private final AtualizarEnderecoUseCase atualizarEnderecoUseCase;
    
    @GetMapping("/{usuarioId}")
    @Operation(summary = "Buscar endereço", description = "Retorna o endereço de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<EnderecoOutput> buscarEndereco(@PathVariable Integer usuarioId) {
        LOGGER.info("Buscando endereço para o usuário com ID: {}", usuarioId);
        
        EnderecoOutput enderecoOutput = buscarEnderecoUseCase.buscarEndereco(usuarioId);
        
        if (enderecoOutput == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(enderecoOutput);
    }
    
    @PutMapping("/{usuarioId}")
    @Operation(summary = "Atualizar endereço", description = "Atualiza o endereço de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Boolean> atualizarEndereco(
            @PathVariable Integer usuarioId,
            @RequestBody @Valid EnderecoInput enderecoInput) {
        LOGGER.info("Atualizando endereço para o usuário com ID: {}", usuarioId);
        
        boolean atualizado = atualizarEnderecoUseCase.atualizarEndereco(
            usuarioId, 
            enderecoInput.getCep(), 
            enderecoInput.getNumero(), 
            enderecoInput.getComplemento()
        );
        
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(true);
    }
    
    @GetMapping("/cep/{cep}")
    @Operation(summary = "Buscar endereço por CEP", description = "Retorna informações de endereço com base no CEP")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informações de CEP encontradas"),
        @ApiResponse(responseCode = "404", description = "CEP não encontrado")
    })
    public ResponseEntity<EnderecoOutput> buscarEnderecoPorCep(@PathVariable String cep) {
        LOGGER.info("Buscando informações para o CEP: {}", cep);
        return ResponseEntity.status(501).build();
    }
}