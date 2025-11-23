package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;

public interface BuscarConsultaPorIdUseCase {
    ConsultaSimpleOutput buscarConsultaPorId(Integer id);
}
