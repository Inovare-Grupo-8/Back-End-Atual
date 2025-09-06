package org.com.imaapi.infrastructure.controller;

@RestController
@RequestMapping("/especialidade")
@RequiredArgsConstructor
public class EspecialidadeController {

    private final EspecialidadeServiceImpl especialidadeService;

    @PostMapping
    @Operation(summary = "Criar uma nova especialidade")
    public ResponseEntity<EspecialidadeDto> criar(@RequestBody EspecialidadeDto especialidadeDto) {
        return especialidadeService.criar(especialidadeDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma especialidade existente")
    public ResponseEntity<EspecialidadeDto> atualizar(@PathVariable Integer id, @RequestBody EspecialidadeDto especialidadeDto) {
        return especialidadeService.atualizar(id, especialidadeDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma especialidade por ID")
    public ResponseEntity<EspecialidadeDto> buscarPorId(@PathVariable Integer id) {
        return especialidadeService.buscarPorId(id);
    }

    @GetMapping
    @Operation(summary = "Listar todas as especialidades")
    public ResponseEntity<List<EspecialidadeDto>> listarTodas() {
        return especialidadeService.listarTodas();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma especialidade")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        return especialidadeService.deletar(id);
    }
}
