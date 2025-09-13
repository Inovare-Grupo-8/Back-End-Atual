package org.com.imaapi.application.useCase.consulta;

import org.springframework.http.ResponseEntity;
import org.com.imaapi.application.dto.consulta.output.ConsultaDto;
import java.util.List;

public interface BuscarTodasConsultasUseCase {
    ResponseEntity<List<ConsultaDto>> getTodasConsultas();
}
