package org.com.imaapi.application.useCase.especialidade;

import org.com.imaapi.application.dto.especialidade.input.EspecialidadeInput;
import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;

public interface CriarEspecialidadeUseCase {
    EspecialidadeOutput criarEspecialidade(EspecialidadeInput especialidadeInput);
}