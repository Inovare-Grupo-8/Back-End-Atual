package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface BuscarProximaConsultaUsuarioUseCase {
    ConsultaOutput buscarProximaConsulta(Integer idUsuario);
}