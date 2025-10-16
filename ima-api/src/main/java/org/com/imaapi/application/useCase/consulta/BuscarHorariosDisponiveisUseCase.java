package org.com.imaapi.application.useCase.consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface BuscarHorariosDisponiveisUseCase {
    List<LocalDateTime> buscarHorariosDisponiveis(LocalDate data, Integer idVoluntario);
}