package org.com.imaapi.infrastructure.controller;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.application.useCase.consulta.ConsultaUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

    @Autowired
    private ConsultaUseCase consultaUseCase;

    @GetMapping("/dia")
    public ResponseEntity<List<ConsultaOutput>> listarConsultasPorDia(
            @RequestParam("user") String user,
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<ConsultaOutput> consultas = consultaUseCase.buscarConsultasPorDia(user, data);
        return ResponseEntity.ok(consultas);
    }
    
    @GetMapping("/dia/paginado")
    public ResponseEntity<Page<ConsultaOutput>> listarConsultasPorDiaPaginado(
            @RequestParam("user") String user,
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "horario") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<ConsultaOutput> consultas = consultaUseCase.buscarConsultasPorDiaPaginado(user, data, pageable);
        return ResponseEntity.ok(consultas);
    }
    
    @GetMapping("/especialidade/{especialidadeId}")
    public ResponseEntity<Page<ConsultaOutput>> listarConsultasPorEspecialidade(
            @PathVariable Integer especialidadeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "horario") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<ConsultaOutput> consultas = consultaUseCase.buscarConsultasPorEspecialidade(especialidadeId, pageable);
        return ResponseEntity.ok(consultas);
    }
    
    @GetMapping("/estatisticas")
    public ResponseEntity<List<ConsultaUseCase.ConsultaStatusCount>> buscarEstatisticasConsultas() {
        return ResponseEntity.ok(consultaUseCase.buscarEstatisticasConsultas());
    }
}