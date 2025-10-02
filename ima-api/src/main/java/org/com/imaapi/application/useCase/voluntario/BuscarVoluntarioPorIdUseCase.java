package org.com.imaapi.application.useCase.voluntario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;

public interface BuscarVoluntarioPorIdUseCase {
    VoluntarioOutput buscarVoluntarioPorId(Integer id);
}