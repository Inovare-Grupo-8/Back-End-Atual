package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface BuscarConsultaPorIdUseCase {
    ConsultaOutput buscarConsultaPorId(Integer id);
}
