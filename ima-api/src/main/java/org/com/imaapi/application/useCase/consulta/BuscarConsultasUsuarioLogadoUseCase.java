package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.input.BuscarConsultasInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;

import java.util.List;

public interface BuscarConsultasUsuarioLogadoUseCase {
    List<ConsultaSimpleOutput> buscarConsultasDoUsuario(BuscarConsultasInput input);
}
