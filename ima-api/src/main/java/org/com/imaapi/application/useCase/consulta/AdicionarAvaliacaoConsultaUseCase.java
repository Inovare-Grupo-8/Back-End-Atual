package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface AdicionarAvaliacaoConsultaUseCase {
    ConsultaOutput adicionarAvaliacao(Integer consultaId, String avaliacao);
}
