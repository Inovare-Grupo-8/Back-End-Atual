package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface AdicionarAvaliacaoConsultaUseCase {
    void adicionarAvaliacao(Integer consultaId, ConsultaOutput input);

    ConsultaOutput adicionarAvaliacao(Integer consultaId, String avaliacao);
}
