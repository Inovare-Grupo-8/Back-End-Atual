package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface RemarcarConsultaUseCase {
    ConsultaOutput remarcarConsulta(Integer id, ConsultaRemarcarInput input);
}
