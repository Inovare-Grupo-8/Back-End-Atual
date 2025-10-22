package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;

import java.util.List;

public interface BuscarTodosAssistentesSociaisUseCase {
    List<AssistenteSocialOutput> executar();
}