package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.enums.Periodo;

import java.time.LocalDate;
import java.util.List;

public interface BuscarConsultasUsuarioLogadoUseCase {
    List<ConsultaOutput> buscarConsultasDoUsuarioLogado(Periodo periodo, LocalDate referencia);
}
