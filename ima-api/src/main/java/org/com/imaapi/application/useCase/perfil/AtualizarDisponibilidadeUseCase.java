package org.com.imaapi.application.useCase.perfil;

import java.util.Map;

public interface AtualizarDisponibilidadeUseCase {
    boolean atualizarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade);
}