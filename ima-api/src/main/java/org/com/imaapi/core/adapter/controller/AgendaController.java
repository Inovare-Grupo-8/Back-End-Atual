package org.com.imaapi.core.adapter.controller;

import org.com.imaapi.model.consulta.output.ConsultaOutput;
import org.com.imaapi.core.adapter.repositoryImpl.ConsultaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private ConsultaServiceImpl consultaService;

    @GetMapping("/dia")
    public ResponseEntity<List<ConsultaOutput>> listarConsultasPorDia(
            @RequestParam("user") String user,
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<ConsultaOutput> consultas = consultaService.buscarConsultasPorDia(user, data);
        return ResponseEntity.ok(consultas);
    }
}