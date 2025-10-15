package org.com.imaapi.infrastructure.controller;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

import org.com.imaapi.application.useCase.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private ConsultaService consultaService;
    @GetMapping("/dia")
    public ResponseEntity<List<ConsultaOutput>> listarConsultasPorDia(
            @RequestParam("user") String user,
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<ConsultaOutput> consultas = consultaService.buscarConsultasPorDia(user, data);
        return ResponseEntity.ok(consultas);
    }


}