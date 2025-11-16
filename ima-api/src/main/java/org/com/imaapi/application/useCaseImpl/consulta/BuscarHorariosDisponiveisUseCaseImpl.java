package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarHorariosDisponiveisUseCase;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuscarHorariosDisponiveisUseCaseImpl implements BuscarHorariosDisponiveisUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarHorariosDisponiveisUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public List<LocalDateTime> buscarHorariosDisponiveis(LocalDate data, Integer idVoluntario) {
        logger.info("Buscando horários disponíveis para voluntário {} na data {}", idVoluntario, data);
        
        try {
            LocalDateTime inicioDia = data.atStartOfDay();
            LocalDateTime fimDia = data.atTime(23, 59, 59);

            // Buscar consultas já marcadas para o voluntário na data
            List<Consulta> consultasMarcadas = consultaRepository
                    .findByVoluntario_IdUsuarioAndHorarioBetween(idVoluntario, inicioDia, fimDia);

            // Gerar horários disponíveis de 00:00 até 23:00
            List<LocalTime> horariosDisponiveis = java.util.stream.IntStream.rangeClosed(0, 23)
                    .mapToObj(h -> LocalTime.of(h, 0))
                    .collect(Collectors.toList());

            // Extrair horários ocupados
            List<LocalTime> horariosOcupados = consultasMarcadas.stream()
                    .map(consulta -> consulta.getHorario().toLocalTime())
                    .collect(Collectors.toList());

            LocalDateTime agora = LocalDateTime.now();

            // Filtrar horários livres
            List<LocalDateTime> horariosLivres = horariosDisponiveis.stream()
                    .filter(horario -> !horariosOcupados.contains(horario))
                    .map(horario -> LocalDateTime.of(data, horario))
                    .filter(horario -> !data.equals(LocalDate.now()) || horario.isAfter(agora))
                    .collect(Collectors.toList());

            logger.info("Encontrados {} horários disponíveis para voluntário {} na data {}", 
                       horariosLivres.size(), idVoluntario, data);

            return horariosLivres;

        } catch (Exception e) {
            logger.error("Erro ao buscar horários disponíveis para voluntário {} na data {}: {}", 
                        idVoluntario, data, e.getMessage());
            throw new RuntimeException("Erro ao buscar horários disponíveis", e);
        }
    }
}