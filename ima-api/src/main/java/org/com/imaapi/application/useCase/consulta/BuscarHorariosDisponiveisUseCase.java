package org.com.imaapi.application.useCase.consulta;

import java.time.LocalDate;
import org.springframework.http.ResponseEntity;

public interface BuscarHorariosDisponiveisUseCase {
    ResponseEntity<?> getHorariosDisponiveis(LocalDate data, Integer idVoluntario);
}
