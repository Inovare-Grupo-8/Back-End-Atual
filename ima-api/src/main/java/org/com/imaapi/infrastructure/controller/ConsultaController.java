package org.com.imaapi.infrastructure.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.com.imaapi.application.dto.consulta.input.ConsultaInput;
import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.application.useCase.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

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
    public ResponseEntity<ConsultaOutput> criarEvento(@RequestBody @Valid ConsultaInput consultaInput) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Criando nova consulta para usuário: {}", consultaInput.getIdUsuario());
        ConsultaOutput output = criarConsultaUseCase.criarConsulta(consultaInput);
        logger.info("Consulta criada com sucesso para usuário: {}", consultaInput.getIdUsuario());
        return ResponseEntity.ok(output);
    }

    @GetMapping("/consultas/minhas")
    public ResponseEntity<List<ConsultaOutput>> getMinhasConsultas() {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Buscando consultas do usuário logado");
        List<ConsultaOutput> consultas = buscarConsultasUsuarioLogadoUseCase.buscarConsultasDoUsuarioLogado();
        logger.info("Total de consultas encontradas: {}", consultas.size());
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<ConsultaOutput> getConsultaPorId(@PathVariable Integer id) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Buscando consulta por id: {}", id);
        ConsultaOutput consulta = buscarConsultaPorIdUseCase.buscarConsultaPorId(id);
        logger.info("Consulta encontrada para id: {}", id);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<ConsultaOutput> cancelarConsulta(@PathVariable Integer id) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Cancelando consulta id: {}", id);
        ConsultaOutput consulta = cancelarConsultaUseCase.cancelarConsulta(id);
        logger.info("Consulta cancelada id: {}", id);
        return ResponseEntity.ok(consulta);
    }

    @PatchMapping("/consultas/{id}/remarcar")
    public ResponseEntity<Void> remarcarConsulta(@PathVariable Integer id, @RequestBody ConsultaRemarcarInput input) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Remarcando consulta id: {}", id);
        remarcarConsultaUseCase.remarcarConsulta(id, input);
        logger.info("Consulta remarcada id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultas/historico")
    public ResponseEntity<List<ConsultaOutput>> listarHistoricoConsultasVoluntario(@RequestParam("user") String user) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Listando histórico de consultas para usuário: {}", user);
        List<ConsultaOutput> historico = buscarHistoricoConsultasUseCase.buscarHistoricoConsultas(user);
        logger.info("Total de consultas no histórico: {}", historico.size());
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/consultas/3-proximas")
    public ResponseEntity<List<ConsultaOutput>> listarProximasConsultas(@RequestParam("user") String user) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Listando próximas 3 consultas para usuário: {}", user);
        List<ConsultaOutput> proximasConsultas = buscarProximasConsultasUseCase.buscarProximasConsultas(user);
        logger.info("Total de próximas consultas encontradas: {}", proximasConsultas.size());
        return ResponseEntity.ok(proximasConsultas);
    }

    @GetMapping("/consultas/todas")
    public ResponseEntity<List<ConsultaOutput>> getTodasConsultas() {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Listando todas as consultas");
        List<ConsultaOutput> consultas = buscarTodasConsultasUseCase.buscarTodasConsultas();
        logger.info("Total de consultas encontradas: {}", consultas.size());
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<?> getHorariosDisponiveis(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data,
            @RequestParam Integer idVoluntario) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Buscando horários disponíveis para voluntário id: {} na data: {}", idVoluntario, data);
        List<LocalDateTime> horarios = buscarHorariosDisponiveisUseCase.buscarHorariosDisponiveis(data, idVoluntario);
        logger.info("Total de horários disponíveis encontrados: {}", horarios.size());
        Map<String, Object> result = new HashMap<>();
        result.put("horarios", horarios);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/consultas/avaliacoes-feedback")
    public ResponseEntity<Map<String, Object>> getAvaliacoesFeedback(@RequestParam String user) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Buscando avaliações e feedbacks para usuário: {}", user);
        Map<String, Object> result = buscarAvaliacoesFeedbackUseCase.buscarAvaliacoesFeedback(user);
        logger.info("Avaliações e feedbacks retornados para usuário: {}", user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/consultas/{id}/feedback")
    public ResponseEntity<ConsultaOutput> adicionarFeedback(@PathVariable Integer id, @RequestBody String feedback) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Adicionando feedback à consulta id: {}", id);
        ConsultaOutput consulta = adicionarFeedbackConsultaUseCase.adicionarFeedback(id, feedback);
        logger.info("Feedback adicionado à consulta id: {}", id);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("/consultas/{id}/avaliacao")
    public ResponseEntity<ConsultaOutput> adicionarAvaliacao(@PathVariable Integer id, @RequestBody String avaliacao) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConsultaController.class);
        logger.info("Adicionando avaliação à consulta id: {}", id);
        ConsultaOutput consulta = adicionarAvaliacaoConsultaUseCase.adicionarAvaliacao(id, avaliacao);
        logger.info("Avaliação adicionada à consulta id: {}", id);
        return ResponseEntity.ok(consulta);
    }
}