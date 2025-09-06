package org.com.imaapi.service.impl;

@Service
@RequiredArgsConstructor
public class EspecialidadeService implements EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    @Override
    @Transactional
    public ResponseEntity<EspecialidadeDto> criar(EspecialidadeDto especialidadeDto) {
        if (especialidadeRepository.existsByNome(especialidadeDto.getNome())) {
            return ResponseEntity.<EspecialidadeDto>status(HttpStatus.CONFLICT).build();
        }

        Especialidade especialidade = new Especialidade();
        especialidade.setNome(especialidadeDto.getNome());

        Especialidade savedEspecialidade = especialidadeRepository.save(especialidade);
        return ResponseEntity.<EspecialidadeDto>status(HttpStatus.CREATED)
                .body(convertToDto(savedEspecialidade));
    }

    @Override
    @Transactional
    public ResponseEntity<EspecialidadeDto> atualizar(Integer id, EspecialidadeDto especialidadeDto) {
        return especialidadeRepository.findById(id)
                .<ResponseEntity<EspecialidadeDto>>map(especialidade -> {
                    if (!especialidade.getNome().equals(especialidadeDto.getNome()) &&
                            especialidadeRepository.existsByNome(especialidadeDto.getNome())) {
                        return ResponseEntity.<EspecialidadeDto>status(HttpStatus.CONFLICT).build();
                    }
                    especialidade.setNome(especialidadeDto.getNome());
                    Especialidade updatedEspecialidade = especialidadeRepository.save(especialidade);
                    return ResponseEntity.<EspecialidadeDto>ok(convertToDto(updatedEspecialidade));
                })
                .orElse(ResponseEntity.<EspecialidadeDto>notFound().build());
    }

    @Override
    public ResponseEntity<EspecialidadeDto> buscarPorId(Integer id) {
        return especialidadeRepository.findById(id)
                .<ResponseEntity<EspecialidadeDto>>map(especialidade ->
                        ResponseEntity.<EspecialidadeDto>ok(convertToDto(especialidade)))
                .orElse(ResponseEntity.<EspecialidadeDto>notFound().build());
    }

    @Override
    public ResponseEntity<List<EspecialidadeDto>> listarTodas() {
        List<EspecialidadeDto> especialidades = especialidadeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(especialidades);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deletar(Integer id) {
        if (!especialidadeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        especialidadeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EspecialidadeDto convertToDto(Especialidade especialidade) {
        EspecialidadeDto dto = new EspecialidadeDto();
        dto.setId(especialidade.getIdEspecialidade());
        dto.setNome(especialidade.getNome());
        return dto;
    }
}
