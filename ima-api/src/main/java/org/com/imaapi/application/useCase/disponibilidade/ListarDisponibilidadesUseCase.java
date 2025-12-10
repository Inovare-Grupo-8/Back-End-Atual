package org.com.imaapi.application.useCase.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;

import java.util.List;

public interface ListarDisponibilidadesUseCase {
    List<DisponibilidadeOutput> listarDisponibilidades();
    List<DisponibilidadeOutput> listarDisponibilidadesPorVoluntario(Integer voluntarioId);
}