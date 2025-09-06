package org.com.imaapi.application.useCaseImpl;

public interface EspecialidadeServiceImpl {
    ResponseEntity<EspecialidadeDto> criar(EspecialidadeDto especialidadeDto);

    ResponseEntity<EspecialidadeDto> atualizar(Integer id, EspecialidadeDto especialidadeDto);

    ResponseEntity<EspecialidadeDto> buscarPorId(Integer id);

    ResponseEntity<List<EspecialidadeDto>> listarTodas();

    ResponseEntity<Void> deletar(Integer id);
}
