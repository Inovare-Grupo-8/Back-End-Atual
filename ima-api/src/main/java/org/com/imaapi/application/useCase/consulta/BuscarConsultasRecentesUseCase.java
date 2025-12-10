package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import java.util.List;

public interface BuscarConsultasRecentesUseCase {
    List<ConsultaOutput> buscarConsultasRecentes(String user);
}