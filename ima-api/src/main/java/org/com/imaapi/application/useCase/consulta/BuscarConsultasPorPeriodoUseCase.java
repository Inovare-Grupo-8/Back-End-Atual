package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

import java.time.LocalDate;
import java.util.List;

public interface BuscarConsultasPorPeriodoUseCase {
    List<ConsultaOutput> buscarPorDia(String user, LocalDate data);
    List<ConsultaOutput> buscarPorSemana(String user, LocalDate referencia);
    List<ConsultaOutput> buscarPorMes(String user, LocalDate referencia);
}