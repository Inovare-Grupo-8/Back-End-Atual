package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.usuario.input.EnderecoInput;
import org.com.imaapi.application.dto.usuario.input.FotoUploadInput;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputAtualizacaoDadosPessoais;
import org.com.imaapi.application.dto.usuario.input.VoluntarioDadosProfissionaisInput;
import org.com.imaapi.application.dto.usuario.output.*;
import org.com.imaapi.application.useCase.perfil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/perfil")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Perfil", description = "Operações relacionadas ao gerenciamento de perfis de usuários")
public class PerfilController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfilController.class);
    
    private final BuscarDadosPessoaisUseCase buscarDadosPessoaisUseCase;
    private final AtualizarDadosPessoaisUseCase atualizarDadosPessoaisUseCase;
    private final BuscarEnderecoUseCase buscarEnderecoUseCase;
    private final AtualizarEnderecoUseCase atualizarEnderecoUseCase;
    private final SalvarFotoUseCase salvarFotoUseCase;
    private final AtualizarDadosProfissionaisUseCase atualizarDadosProfissionaisUseCase;
    private final CriarDisponibilidadeUseCase criarDisponibilidadeUseCase;
    private final AtualizarDisponibilidadeUseCase atualizarDisponibilidadeUseCase;
    private final AtualizarDadosPessoaisCompletoUseCase atualizarDadosPessoaisCompletoUseCase;

    @GetMapping("/{tipo}/dados-pessoais")
    public ResponseEntity<UsuarioDadosPessoaisOutput> buscarDadosPessoais(
            @RequestParam Integer usuarioId, @PathVariable String tipo) {
        UsuarioDadosPessoaisOutput usuarioOutput = buscarDadosPessoaisUseCase.buscarDadosPessoais(usuarioId);
        if (usuarioOutput == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioOutput);
    }

    @PatchMapping("/{tipo}/dados-pessoais")
    public ResponseEntity<UsuarioOutput> atualizarDadosPessoais(
            @RequestParam Integer usuarioId,
            @PathVariable String tipo,
            @RequestBody UsuarioInputAtualizacaoDadosPessoais usuarioInputAtualizacaoDadosPessoais) {
        UsuarioOutput usuarioOutput = atualizarDadosPessoaisUseCase.atualizarDadosPessoais(usuarioId, usuarioInputAtualizacaoDadosPessoais);
        if (usuarioOutput == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(usuarioOutput);
    }

    @GetMapping("/{tipo}/endereco")
    public ResponseEntity<EnderecoOutput> buscarEndereco(
            @RequestParam Integer usuarioId, @PathVariable String tipo) {
        EnderecoOutput enderecoOutput = buscarEnderecoUseCase.buscarEndereco(usuarioId);
        if (enderecoOutput == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enderecoOutput);
    }

    @PutMapping("/{tipo}/endereco")
    public ResponseEntity<Void> atualizarEndereco(
            @RequestParam Integer usuarioId,
            @PathVariable String tipo,
            @RequestBody @Valid EnderecoInput enderecoInput) {
        boolean atualizado = atualizarEnderecoUseCase.atualizarEndereco(
                usuarioId, enderecoInput.getCep(), enderecoInput.getNumero(), enderecoInput.getComplemento());
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tipo}/foto")
    @Operation(summary = "Upload de foto do perfil", 
               description = "Realiza o upload de uma foto para o perfil do usuário com validações de tipo e tamanho")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Foto enviada com sucesso",
                    content = @Content(schema = @Schema(implementation = FotoUploadOutput.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou arquivo com problema",
                    content = @Content(schema = @Schema(implementation = FotoUploadOutput.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = FotoUploadOutput.class)))
    })
    public ResponseEntity<FotoUploadOutput> uploadFoto(
            @Parameter(description = "ID do usuário") @RequestParam Integer usuarioId,
            @Parameter(description = "Tipo do usuário (assistido, voluntario, assistente_social)") @PathVariable String tipo,
            @Parameter(description = "Arquivo de imagem (máximo 1MB, formatos: JPEG, PNG, GIF, WEBP)") @RequestParam("file") MultipartFile file) {
        
        LOGGER.info("Iniciando upload de foto para usuário ID: {}, tipo: {}", usuarioId, tipo);
        
        // Criar DTO de input
        FotoUploadInput input = new FotoUploadInput(usuarioId, tipo, file);
        
        // Validações usando método utilitário
        FotoUploadOutput validationError = validarFotoInput(input, usuarioId);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        try {
            String fotoUrl = salvarFotoUseCase.salvarFoto(usuarioId, tipo, file);
            
            LOGGER.info("Foto salva com sucesso para usuário ID: {}, URL: {}", usuarioId, fotoUrl);
            
            // Criar resposta usando DTO de output
            FotoUploadOutput output = FotoUploadOutput.sucesso(
                    "Foto salva com sucesso.",
                    fotoUrl,
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType()
            );
            
            return ResponseEntity.ok(output);
            
        } catch (IOException e) {
            LOGGER.error("Erro ao salvar foto para usuário ID: {}: {}", usuarioId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FotoUploadOutput.erro("Erro interno ao salvar a foto."));
        } catch (Exception e) {
            LOGGER.error("Erro inesperado ao processar upload para usuário ID: {}: {}", usuarioId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FotoUploadOutput.erro("Erro inesperado ao processar o upload."));
        }
    }

    private FotoUploadOutput validarFotoInput(FotoUploadInput input, Integer usuarioId) {
        if (input.isArquivoVazio()) {
            LOGGER.warn("Arquivo vazio recebido para usuário ID: {}", usuarioId);
            return FotoUploadOutput.erro("O arquivo não pode estar vazio.");
        }

        if (!input.isTipoValido()) {
            LOGGER.warn("Tipo de arquivo inválido recebido: {} para usuário ID: {}", 
                    input.getArquivo().getContentType(), usuarioId);
            return FotoUploadOutput.erro("O arquivo deve ser uma imagem válida (JPEG, PNG, GIF, WEBP).");
        }

        if (!input.isTamanhoValido()) {
            LOGGER.warn("Arquivo muito grande ({} bytes) recebido para usuário ID: {}", 
                    input.getArquivo().getSize(), usuarioId);
            return FotoUploadOutput.erro("O tamanho máximo permitido é 1MB.");
        }

        return null; // Validação passou
    }

    @PatchMapping("/voluntario/dados-profissionais")
    public ResponseEntity<Void> atualizarDadosProfissionais(
            @RequestParam Integer usuarioId,
            @RequestBody @Valid VoluntarioDadosProfissionaisInput dadosProfissionais) {
        boolean atualizado = atualizarDadosProfissionaisUseCase.atualizarDadosProfissionais(usuarioId, dadosProfissionais);
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/voluntario/disponibilidade")
    public ResponseEntity<Void> criarDisponibilidade(
            @RequestParam Integer usuarioId,
            @RequestBody Map<String, Object> disponibilidade) {
        boolean criado = criarDisponibilidadeUseCase.criarDisponibilidade(usuarioId, disponibilidade);
        if (!criado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/voluntario/disponibilidade")
    public ResponseEntity<Void> atualizarDisponibilidade(
            @RequestParam Integer usuarioId,
            @RequestBody Map<String, Object> disponibilidade) {
        boolean atualizado = atualizarDisponibilidadeUseCase.atualizarDisponibilidade(usuarioId, disponibilidade);
        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/assistente-social")
//    public ResponseEntity<AssistenteSocialOutput> buscarPerfilAssistenteSocial(@RequestParam Integer usuarioId) {
//        try {
//            AssistenteSocialOutput perfil = buscarDadosPessoaisUseCase.buscarAssistenteSocial(usuarioId);
//            if (perfil == null) {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.ok(perfil);
//        } catch (Exception e) {
//            LOGGER.error("Erro ao buscar perfil do assistente social: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PatchMapping("/assistente-social/dados-profissionais")
    @ResponseBody
    public ResponseEntity<Void> atualizarDadosProfissionaisAssistenteSocial(
            @RequestParam Integer usuarioId,
            @RequestBody @Valid VoluntarioDadosProfissionaisInput dadosProfissionais) {
        try {
            LOGGER.info("Atualizando dados profissionais para assistente social com ID: {}", usuarioId);
            boolean atualizado = atualizarDadosProfissionaisUseCase.atualizarDadosProfissionais(usuarioId, dadosProfissionais);
            if (!atualizado) {
                LOGGER.warn("Assistente social não encontrado com ID: {}", usuarioId);
                return ResponseEntity.notFound().build();
            }
            LOGGER.info("Dados profissionais atualizados com sucesso para assistente social com ID: {}", usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar dados profissionais do assistente social: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }    
  
    @PatchMapping("/assistente-social/dados-pessoais")
    @ResponseBody
    public ResponseEntity<UsuarioDadosPessoaisOutput> atualizarDadosPessoaisAssistenteSocial(
            @RequestParam Integer usuarioId,
            @RequestBody @Valid UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        try {
            LOGGER.info("Atualizando dados pessoais para assistente social com ID: {}", usuarioId);
            UsuarioDadosPessoaisOutput dadosAtualizados = atualizarDadosPessoaisCompletoUseCase.atualizarDadosPessoaisCompleto(usuarioId, dadosPessoais);
            if (dadosAtualizados == null) {
                LOGGER.warn("Assistente social não encontrado com ID: {}", usuarioId);
                return ResponseEntity.notFound().build();
            }
            LOGGER.info("Dados pessoais atualizados com sucesso para assistente social com ID: {}", usuarioId);
            return ResponseEntity.ok(dadosAtualizados);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar dados pessoais do assistente social: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}