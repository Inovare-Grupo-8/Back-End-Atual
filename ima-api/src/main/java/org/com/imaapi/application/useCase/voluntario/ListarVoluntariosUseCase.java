package org.com.imaapi.application.useCase.voluntario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;

import java.util.List;

public interface ListarVoluntariosUseCase {
    List<VoluntarioOutput> listarVoluntarios();
}