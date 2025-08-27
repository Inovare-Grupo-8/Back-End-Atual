package org.com.imaapi.core.adapter.controller;

import org.com.imaapi.model.consulta.output.ConsultaOutput;
import org.com.imaapi.core.adapter.repositoryImpl.ConsultaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private ConsultaServiceImpl consultaService;
    
    @GetMapping("/{consultaId}")
    public ResponseEntity<ConsultaOutput> detalharConsulta(
            @PathVariable Integer consultaId,
            @RequestParam("user") String user) {
        ConsultaOutput consulta = consultaService.buscarConsultaPorIdEUsuario(consultaId, user);
        return ResponseEntity.ok(consulta);
    }
}
