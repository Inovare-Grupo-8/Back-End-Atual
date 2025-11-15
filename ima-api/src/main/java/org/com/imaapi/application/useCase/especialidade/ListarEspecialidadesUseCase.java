package org.com.imaapi.application.useCase.especialidade;

import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;

import java.util.List;

public interface ListarEspecialidadesUseCase {
    List<EspecialidadeOutput> listarEspecialidades();
}