package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.consulta.input.AvaliacaoInput;
import org.com.imaapi.application.dto.consulta.input.FeedbackInput;
import org.com.imaapi.application.dto.consulta.output.AvaliacaoOutput;
import org.com.imaapi.application.dto.consulta.output.FeedbackOutput;
import org.com.imaapi.application.useCase.avaliacao.AvaliacaoFeedbackUseCase;
import org.com.imaapi.domain.model.AvaliacaoConsulta;
import org.com.imaapi.domain.model.FeedbackConsulta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consultas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Avaliações e Feedbacks", description = "Operações relacionadas a avaliações e feedbacks de consultas")
public class AvaliacaoFeedbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoFeedbackController.class);
    
    private final AvaliacaoFeedbackUseCase avaliacaoFeedbackUseCase;
    
    // Endpoints para Avaliações
    
    @PostMapping("/{idConsulta}/avaliacoes")
    @Operation(summary = "Criar avaliação", description = "Cria uma nova avaliação para uma consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    public ResponseEntity<AvaliacaoOutput> criarAvaliacao(
            @PathVariable Integer idConsulta,
            @RequestBody @Valid AvaliacaoInput avaliacaoInput) {
        LOGGER.info("Criando avaliação para consulta com ID: {}", idConsulta);
        
        AvaliacaoConsulta avaliacao = avaliacaoFeedbackUseCase.criarAvaliacao(idConsulta, avaliacaoInput);
        
        if (avaliacao == null) {
            return ResponseEntity.notFound().build();
        }
        
        AvaliacaoOutput output = AvaliacaoOutput.fromEntity(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
    
    @GetMapping("/avaliacoes/{idAvaliacao}")
    @Operation(summary = "Buscar avaliação por ID", description = "Retorna uma avaliação específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação encontrada"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<AvaliacaoOutput> buscarAvaliacaoPorId(@PathVariable Integer idAvaliacao) {
        LOGGER.info("Buscando avaliação com ID: {}", idAvaliacao);
        
        Optional<AvaliacaoConsulta> avaliacaoOptional = avaliacaoFeedbackUseCase.buscarAvaliacaoPorId(idAvaliacao);
        
        if (avaliacaoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        AvaliacaoOutput output = AvaliacaoOutput.fromEntity(avaliacaoOptional.get());
        return ResponseEntity.ok(output);
    }
    
    @GetMapping("/{idConsulta}/avaliacoes")
    @Operation(summary = "Buscar avaliações por consulta", description = "Retorna todas as avaliações associadas a uma consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
        @ApiResponse(responseCode = "404", description = "Nenhuma avaliação encontrada")
    })
    public ResponseEntity<List<AvaliacaoOutput>> buscarAvaliacoesPorConsulta(@PathVariable Integer idConsulta) {
        LOGGER.info("Buscando avaliações para consulta com ID: {}", idConsulta);
        
        List<AvaliacaoConsulta> avaliacoes = avaliacaoFeedbackUseCase.buscarAvaliacoesPorConsulta(idConsulta);
        
        if (avaliacoes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<AvaliacaoOutput> outputs = avaliacoes.stream()
                .map(AvaliacaoOutput::fromEntity)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(outputs);
    }
    
    // Endpoints para Feedbacks
    
    @PostMapping("/{idConsulta}/feedbacks")
    @Operation(summary = "Criar feedback", description = "Cria um novo feedback para uma consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    public ResponseEntity<FeedbackOutput> criarFeedback(
            @PathVariable Integer idConsulta,
            @RequestBody @Valid FeedbackInput feedbackInput) {
        LOGGER.info("Criando feedback para consulta com ID: {}", idConsulta);
        
        FeedbackConsulta feedback = avaliacaoFeedbackUseCase.criarFeedback(idConsulta, feedbackInput);
        
        if (feedback == null) {
            return ResponseEntity.notFound().build();
        }
        
        FeedbackOutput output = FeedbackOutput.fromEntity(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
    
    @GetMapping("/feedbacks/{idFeedback}")
    @Operation(summary = "Buscar feedback por ID", description = "Retorna um feedback específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedback encontrado"),
        @ApiResponse(responseCode = "404", description = "Feedback não encontrado")
    })
    public ResponseEntity<FeedbackOutput> buscarFeedbackPorId(@PathVariable Integer idFeedback) {
        LOGGER.info("Buscando feedback com ID: {}", idFeedback);
        
        Optional<FeedbackConsulta> feedbackOptional = avaliacaoFeedbackUseCase.buscarFeedbackPorId(idFeedback);
        
        if (feedbackOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        FeedbackOutput output = FeedbackOutput.fromEntity(feedbackOptional.get());
        return ResponseEntity.ok(output);
    }
    
    @GetMapping("/{idConsulta}/feedbacks")
    @Operation(summary = "Buscar feedbacks por consulta", description = "Retorna todos os feedbacks associados a uma consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedbacks encontrados"),
        @ApiResponse(responseCode = "404", description = "Nenhum feedback encontrado")
    })
    public ResponseEntity<List<FeedbackOutput>> buscarFeedbacksPorConsulta(@PathVariable Integer idConsulta) {
        LOGGER.info("Buscando feedbacks para consulta com ID: {}", idConsulta);
        
        List<FeedbackConsulta> feedbacks = avaliacaoFeedbackUseCase.buscarFeedbacksPorConsulta(idConsulta);
        
        if (feedbacks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<FeedbackOutput> outputs = feedbacks.stream()
                .map(FeedbackOutput::fromEntity)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(outputs);
    }
}