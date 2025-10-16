package org.com.imaapi.application.useCase.especialidade;

import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;

public interface BuscarEspecialidadePorIdUseCase {
    EspecialidadeOutput buscarEspecialidadePorId(Integer id);
}