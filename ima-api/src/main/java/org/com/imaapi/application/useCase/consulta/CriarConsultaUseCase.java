
package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.application.dto.consulta.input.ConsultaInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;

public interface CriarConsultaUseCase {
    ConsultaOutput criarConsulta(ConsultaInput input);
}
