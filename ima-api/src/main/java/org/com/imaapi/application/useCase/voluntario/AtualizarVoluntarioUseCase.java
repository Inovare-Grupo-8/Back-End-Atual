package org.com.imaapi.application.useCase.voluntario;

import org.com.imaapi.application.dto.usuario.input.VoluntarioInput;

public interface AtualizarVoluntarioUseCase {
    void executar(VoluntarioInput voluntarioInput);
}