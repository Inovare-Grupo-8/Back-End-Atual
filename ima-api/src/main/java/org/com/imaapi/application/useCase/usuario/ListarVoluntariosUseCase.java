package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioListagemOutput;

import java.util.List;

public interface ListarVoluntariosUseCase {
    List<VoluntarioListagemOutput> executar();
}