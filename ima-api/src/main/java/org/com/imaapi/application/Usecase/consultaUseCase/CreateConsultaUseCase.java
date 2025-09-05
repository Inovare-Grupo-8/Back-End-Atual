package org.com.imaapi.application.Usecase.consultaUseCase;

<file file_path="/src/main/java/org/com/imaapi/application/Usecase/consulta/consulta/CreateConsultaUseCase.java">
        package org.com.imaapi.application.Usecase.consulta.consulta;

import org.com.imaapi.application.Usecase.Consulta.dto.ConsultaOutputDto;
import org.com.imaapi.application.Usecase.Consulta.dto.CreateConsultaInput;

public interface CreateConsultaUseCase extends ConsultaUseCase {
    ConsultaOutputDto execute(CreateConsultaInput input);
}
