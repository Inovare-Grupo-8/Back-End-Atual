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
        ConsultaOutput output = criarConsultaUseCase.criarConsulta(consultaInput);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/consultas/minhas")
    public ResponseEntity<List<ConsultaOutput>> getMinhasConsultas() {
        List<ConsultaOutput> consultas = buscarConsultasUsuarioLogadoUseCase.buscarConsultasDoUsuarioLogado();
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<ConsultaOutput> getConsultaPorId(@PathVariable Integer id) {
        ConsultaOutput consulta = buscarConsultaPorIdUseCase.buscarConsultaPorId(id);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<ConsultaOutput> cancelarConsulta(@PathVariable Integer id) {
        ConsultaOutput consulta = cancelarConsultaUseCase.cancelarConsulta(id);
        return ResponseEntity.ok(consulta);
    }

    @PatchMapping("/consultas/{id}/remarcar")
    public ResponseEntity<Void> remarcarConsulta(@PathVariable Integer id, @RequestBody ConsultaRemarcarInput input) {
        remarcarConsultaUseCase.remarcarConsulta(id, input);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultas/historico")
    public ResponseEntity<List<ConsultaOutput>> listarHistoricoConsultasVoluntario(@RequestParam("user") String user) {
        List<ConsultaOutput> historico = buscarHistoricoConsultasUseCase.buscarHistoricoConsultas(user);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/consultas/3-proximas")
    public ResponseEntity<List<ConsultaOutput>> listarProximasConsultas(@RequestParam("user") String user) {
        List<ConsultaOutput> proximasConsultas = buscarProximasConsultasUseCase.buscarProximasConsultas(user);
        return ResponseEntity.ok(proximasConsultas);
    }

    @GetMapping("/consultas/todas")
    public ResponseEntity<List<ConsultaOutput>> getTodasConsultas() {
        List<ConsultaOutput> consultas = buscarTodasConsultasUseCase.buscarTodasConsultas();
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<?> getHorariosDisponiveis(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data,
            @RequestParam Integer idVoluntario) {
        List<LocalDateTime> horarios = buscarHorariosDisponiveisUseCase.buscarHorariosDisponiveis(data, idVoluntario);
        Map<String, Object> result = new HashMap<>();
        result.put("horarios", horarios);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/consultas/avaliacoes-feedback")
    public ResponseEntity<Map<String, Object>> getAvaliacoesFeedback(@RequestParam String user) {
        Map<String, Object> result = buscarAvaliacoesFeedbackUseCase.buscarAvaliacoesFeedback(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/consultas/{id}/feedback")
    public ResponseEntity<ConsultaOutput> adicionarFeedback(@PathVariable Integer id, @RequestBody String feedback) {
        ConsultaOutput consulta = adicionarFeedbackConsultaUseCase.adicionarFeedback(id, feedback);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("/consultas/{id}/avaliacao")
    public ResponseEntity<ConsultaOutput> adicionarAvaliacao(@PathVariable Integer id, @RequestBody String avaliacao) {
        ConsultaOutput consulta = adicionarAvaliacaoConsultaUseCase.adicionarAvaliacao(id, avaliacao);
        return ResponseEntity.ok(consulta);
    }
}