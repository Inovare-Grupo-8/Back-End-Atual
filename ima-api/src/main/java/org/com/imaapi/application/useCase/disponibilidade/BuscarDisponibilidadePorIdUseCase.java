package org.com.imaapi.application.useCase.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;

public interface BuscarDisponibilidadePorIdUseCase {
    DisponibilidadeOutput buscarDisponibilidadePorId(Integer id);
}