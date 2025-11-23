package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;

public interface RemarcarConsultaUseCase {
    ConsultaSimpleOutput remarcarConsulta(Integer id, ConsultaRemarcarInput input);
}
