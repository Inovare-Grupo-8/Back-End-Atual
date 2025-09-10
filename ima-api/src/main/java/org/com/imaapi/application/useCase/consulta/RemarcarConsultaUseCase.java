package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;

public interface RemarcarConsultaUseCase {
    void remarcarConsulta(Integer id, ConsultaRemarcarInput input);
}
