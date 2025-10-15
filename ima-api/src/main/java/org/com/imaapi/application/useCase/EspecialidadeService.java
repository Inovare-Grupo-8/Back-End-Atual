package org.com.imaapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.especialidade.input.EspecialidadeInput;
import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    @Transactional
    public ResponseEntity<EspecialidadeOutput> criar(EspecialidadeInput especialidadeInput) {
        if (especialidadeRepository.existsByNome(especialidadeInput.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Especialidade especialidade = new Especialidade();
        especialidade.setNome(especialidadeInput.getNome());

        Especialidade savedEspecialidade = especialidadeRepository.save(especialidade);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToDto(savedEspecialidade));
    }

    @Transactional
    public ResponseEntity<EspecialidadeOutput> atualizar(Integer id, EspecialidadeInput especialidadeInput) {
        return especialidadeRepository.findById(id)
                .map(especialidade -> {
                    if (!especialidade.getNome().equals(especialidadeInput.getNome()) &&
                            especialidadeRepository.existsByNome(especialidadeInput.getNome())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }
                    especialidade.setNome(especialidadeInput.getNome());
                    Especialidade updatedEspecialidade = especialidadeRepository.save(especialidade);
                    return ResponseEntity.ok(convertToDto(updatedEspecialidade));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<EspecialidadeOutput> buscarPorId(Integer id) {
        return especialidadeRepository.findById(id)
                .map(especialidade -> ResponseEntity.ok(convertToDto(Especialidade)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<EspecialidadeOutput>> listarTodas() {
        List<EspecialidadeOutput> especialidades = especialidadeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(especialidades);
    }

    @Transactional
    public ResponseEntity<Void> deletar(Integer id) {
        if (!especialidadeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        especialidadeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EspecialidadeOutput convertToDto(Especialidade especialidade) {
        EspecialidadeOutput dto = new EspecialidadeOutput();
        dto.setId(especialidade.getIdEspecialidade());
        dto.setNome(especialidade.getNome());
        return dto;
    }
}
