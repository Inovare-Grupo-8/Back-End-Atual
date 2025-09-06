package org.com.imaapi.infrastructure.controller;

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
