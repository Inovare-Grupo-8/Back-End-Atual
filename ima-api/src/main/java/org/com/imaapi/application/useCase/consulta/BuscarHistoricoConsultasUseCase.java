package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;
import java.util.List;

public interface BuscarHistoricoConsultasUseCase {
    List<ConsultaSimpleOutput> buscarHistoricoConsultas(Integer userId);
}
