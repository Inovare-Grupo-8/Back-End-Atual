package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface CancelarConsultaUseCase {
    ConsultaOutput cancelarConsulta(Integer consultaId);
}
