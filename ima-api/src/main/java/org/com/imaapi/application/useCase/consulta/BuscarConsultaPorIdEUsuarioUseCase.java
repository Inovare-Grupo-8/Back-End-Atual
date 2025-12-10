package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface BuscarConsultaPorIdEUsuarioUseCase {
    ConsultaOutput buscarConsultaPorIdEUsuario(Integer consultaId, String user);
}
