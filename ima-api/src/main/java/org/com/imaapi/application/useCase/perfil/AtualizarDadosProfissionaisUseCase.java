package org.com.imaapi.application.useCase.perfil;

import org.com.imaapi.application.dto.usuario.input.VoluntarioDadosProfissionaisInput;

public interface AtualizarDadosProfissionaisUseCase {
    boolean atualizarDadosProfissionais(Integer usuarioId, VoluntarioDadosProfissionaisInput dadosProfissionais);
}