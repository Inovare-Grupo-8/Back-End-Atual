package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface AdicionarFeedbackConsultaUseCase {
    ConsultaOutput adicionarFeedback(Integer consultaId, String feedback);
}
