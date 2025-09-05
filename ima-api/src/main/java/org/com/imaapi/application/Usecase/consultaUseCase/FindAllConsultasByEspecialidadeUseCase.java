package org.com.imaapi.application.Usecase.consultaUseCase;

import java.util.List;
import org.com.imaapi.application.usecases.consulta.dto.ConsultaOutputDto;

public interface FindAllConsultasByEspecialidadeUseCase extends ConsultaUseCase {
    List<ConsultaOutputDto> execute(ConsultaEspecialidadeInput input);
}
