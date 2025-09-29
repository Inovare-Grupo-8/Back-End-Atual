package org.com.imaapi.application.useCase.voluntario;

import org.com.imaapi.application.dto.usuario.input.VoluntarioInput;
import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;

public interface CadastrarVoluntarioUseCase {
    VoluntarioOutput cadastrarVoluntario(VoluntarioInput voluntarioInput);
}