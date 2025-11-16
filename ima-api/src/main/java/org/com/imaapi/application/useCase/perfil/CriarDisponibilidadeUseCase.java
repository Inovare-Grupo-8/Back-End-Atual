package org.com.imaapi.application.useCase.perfil;

import java.util.Map;

public interface CriarDisponibilidadeUseCase {
    boolean criarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade);
}