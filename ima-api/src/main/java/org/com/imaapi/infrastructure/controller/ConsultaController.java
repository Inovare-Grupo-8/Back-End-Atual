package org.com.imaapi.infrastructure.controller;

import jakarta.validation.Valid;
import org.com.imaapi.application.dto.consulta.input.BuscarConsultasInput;
import org.com.imaapi.application.dto.consulta.input.ConsultaInput;
import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;
import org.com.imaapi.application.useCase.consulta.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    
    private static final Logger logger = LoggerFactory.getLogger(ConsultaController.class);

    @Autowired
    private CriarConsultaUseCase criarConsultaUseCase;

    @Autowired
    private BuscarConsultasUsuarioLogadoUseCase buscarConsultasUsuarioLogadoUseCase;

    @Autowired
    private BuscarConsultaPorIdUseCase buscarConsultaPorIdUseCase;

    @Autowired
    private CancelarConsultaUseCase cancelarConsultaUseCase;

    @Autowired
    private RemarcarConsultaUseCase remarcarConsultaUseCase;

    @Autowired
    private BuscarHistoricoConsultasUseCase buscarHistoricoConsultasUseCase;

    @Autowired
    private BuscarProximasConsultasUseCase buscarProximasConsultasUseCase;

    @Autowired
    private BuscarTodasConsultasUseCase buscarTodasConsultasUseCase;

    @Autowired
    private BuscarHorariosDisponiveisUseCase buscarHorariosDisponiveisUseCase;

    @Autowired
    private BuscarAvaliacoesFeedbackUseCase buscarAvaliacoesFeedbackUseCase;

    @Autowired
    private AdicionarFeedbackConsultaUseCase adicionarFeedbackConsultaUseCase;

    @Autowired
    private AdicionarAvaliacaoConsultaUseCase adicionarAvaliacaoConsultaUseCase;

    @PostMapping
    public ResponseEntity<ConsultaSimpleOutput> criarEvento(@RequestBody @Valid ConsultaInput consultaInput) {
        logger.info("Criando nova consulta");
        ConsultaSimpleOutput output = criarConsultaUseCase.criarConsulta(consultaInput);
        logger.info("Consulta criada com sucesso");
        return ResponseEntity.ok(output);
    }

    @GetMapping("/consultas/minhas")
    public ResponseEntity<List<ConsultaSimpleOutput>> getMinhasConsultas(
            @RequestParam(required = false) Integer userId,
            @RequestParam(defaultValue = "ATUAL") String periodo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReferencia) {
        
        logger.info("Buscando consultas do usuário - userId: {}, periodo: {}", userId, periodo);
        
        BuscarConsultasInput input = new BuscarConsultasInput(
            userId,
            periodo,
            dataReferencia != null ? dataReferencia : LocalDate.now()
        );
        
        List<ConsultaSimpleOutput> consultas = buscarConsultasUsuarioLogadoUseCase.buscarConsultasDoUsuario(input);
        
        logger.info("Total de consultas encontradas: {}", consultas.size());
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<ConsultaOutput> getConsultaPorId(@PathVariable Integer id) {
        logger.info("Buscando consulta por id: {}", id);
        return Optional.ofNullable(buscarConsultaPorIdUseCase.buscarConsultaPorId(id))
                .map(consulta -> {
                    logger.info("Consulta encontrada para id: {}", id);
                    return ResponseEntity.ok(consulta);
                })
                .orElseGet(() -> ResponseEntity.<ConsultaOutput>notFound().build());
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<ConsultaOutput> cancelarConsulta(@PathVariable Integer id) {
        logger.info("Cancelando consulta id: {}", id);
        ConsultaOutput consulta = cancelarConsultaUseCase.cancelarConsulta(id);
        logger.info("Consulta cancelada id: {}", id);
        return ResponseEntity.ok(consulta);
    }

    @PatchMapping("/consultas/{id}/remarcar")
    public ResponseEntity<ConsultaOutput> remarcarConsulta(@PathVariable Integer id, @RequestBody ConsultaRemarcarInput input) {
        logger.info("Remarcando consulta id: {}", id);
        ConsultaOutput consulta = remarcarConsultaUseCase.remarcarConsulta(id, input);
        logger.info("Consulta remarcada id: {}", id);
        return ResponseEntity.ok(consulta);
    }

    @GetMapping("/consultas/historico")
    public ResponseEntity<Map<String, Object>> listarHistoricoConsultas(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String user) {
        
        // Se userId não foi fornecido, tenta usar um valor padrão baseado no parâmetro user
        Integer userIdFinal = userId;
        if (userIdFinal == null && user != null && !user.trim().isEmpty()) {
            // Se passou um email ou string, usa ID padrão 1 para teste
            userIdFinal = 1;
            logger.info("Parâmetro 'user' fornecido: '{}', usando userId padrão: {}", user, userIdFinal);
        }
        
        logger.info("Listando histórico de consultas para usuário ID: {}", userIdFinal);
        List<ConsultaSimpleOutput> historico = buscarHistoricoConsultasUseCase.buscarHistoricoConsultas(userIdFinal);
        logger.info("Total de consultas no histórico: {}", historico.size());
        
        // Response mais informativo
        Map<String, Object> response = Map.of(
            "consultas", historico,
            "total", historico.size(),
            "userId", userIdFinal,
            "tipo", "historico"
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consultas/3-proximas")
    public ResponseEntity<List<ConsultaOutput>> listarProximasConsultas(
            @RequestParam(value = "user", defaultValue = "assistido") String user,
            @RequestParam(required = false) Integer userId) {
        logger.info("Listando próximas 3 consultas para usuário: {}", user);
        try {
            return Optional.ofNullable(buscarProximasConsultasUseCase.buscarProximasConsultas(user, userId))
                    .map(proximasConsultas -> {
                        logger.info("Total de próximas consultas encontradas: {}", proximasConsultas.size());
                        return ResponseEntity.ok(proximasConsultas);
                    })
                    .orElseGet(() -> ResponseEntity.ok(List.of()));
        } catch (RuntimeException ex) {
            logger.warn("Falha ao buscar próximas consultas: {}", ex.getMessage());
            return ResponseEntity.ok(List.of());
        }
    }

    @GetMapping("/consultas/todas")
    public ResponseEntity<List<ConsultaOutput>> getTodasConsultas() {
        logger.info("Listando todas as consultas");
        return Optional.ofNullable(buscarTodasConsultasUseCase.buscarTodasConsultas())
                .map(consultas -> {
                    logger.info("Total de consultas encontradas: {}", consultas.size());
                    return ResponseEntity.ok(consultas);
                })
                .orElseGet(() -> ResponseEntity.ok(List.of()));
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<Map<String, Object>> getHorariosDisponiveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Integer idVoluntario,
            @RequestParam(required = false) Integer userId) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        Integer alvoId = idVoluntario != null ? idVoluntario : userId;
        if (alvoId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "idVoluntario ou userId é obrigatório"));
        }
        logger.info("Buscando horários disponíveis para voluntário id: {} na data: {}", alvoId, data);
        List<LocalDateTime> horarios = buscarHorariosDisponiveisUseCase.buscarHorariosDisponiveis(data, alvoId);
        logger.info("Total de horários disponíveis encontrados: {}", horarios.size());
        return ResponseEntity.ok(Map.of(
                "data", data,
                "idVoluntario", alvoId,
                "horarios", horarios
        ));
    }

    @GetMapping({"/consultas/avaliacoes-feedback", "/avaliacoes-feedback"})
    public ResponseEntity<Map<String, Object>> getAvaliacoesFeedback(@RequestParam(value = "user", defaultValue = "assistido") String user) {
        logger.info("Buscando avaliações e feedbacks para usuário: {}", user);
        try {
            Map<String, Object> result = buscarAvaliacoesFeedbackUseCase.buscarAvaliacoesFeedback(user);
            logger.info("Avaliações e feedbacks retornados para usuário: {}", user);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            logger.warn("Falha ao buscar avaliações/feedback: {}", ex.getMessage());
            return ResponseEntity.ok(Map.of("feedbacks", List.of(), "avaliacoes", List.of()));
        }
    }

    @PostMapping("/consultas/{id}/feedback")
    public ResponseEntity<ConsultaOutput> adicionarFeedback(@PathVariable Integer id, @RequestBody String feedback) {
        logger.info("Adicionando feedback à consulta id: {}", id);
        ConsultaOutput consulta = adicionarFeedbackConsultaUseCase.adicionarFeedback(id, feedback);
        logger.info("Feedback adicionado à consulta id: {}", id);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("/consultas/{id}/avaliacao")
    public ResponseEntity<ConsultaOutput> adicionarAvaliacao(@PathVariable Integer id, @RequestBody String avaliacao) {
        logger.info("Adicionando avaliação à consulta id: {}", id);
        ConsultaOutput consulta = adicionarAvaliacaoConsultaUseCase.adicionarAvaliacao(id, avaliacao);
        logger.info("Avaliação adicionada à consulta id: {}", id);
        return ResponseEntity.ok(consulta);
    }
}