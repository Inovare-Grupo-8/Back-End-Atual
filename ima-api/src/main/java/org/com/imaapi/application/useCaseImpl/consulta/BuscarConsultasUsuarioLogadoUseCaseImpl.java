package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultasUsuarioLogadoUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.Periodo;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuscarConsultasUsuarioLogadoUseCaseImpl implements BuscarConsultasUsuarioLogadoUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultasUsuarioLogadoUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public List<ConsultaOutput> buscarConsultasDoUsuarioLogado(Periodo periodo, LocalDate referencia) {
        logger.info("Buscando consultas do usuário logado para período: {} com referência: {}", periodo, referencia);

        try {
            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            Integer userId = usuarioLogado.getIdUsuario();

            LocalDateTime inicio;
            LocalDateTime fim;

            switch (periodo) {
                case DIA:
                    inicio = referencia.atStartOfDay();
                    fim = referencia.atTime(23, 59, 59);
                    break;
                case SEMANA:
                    DayOfWeek diaSemana = referencia.getDayOfWeek();
                    int diasParaSegunda = diaSemana.getValue() - DayOfWeek.MONDAY.getValue();
                    LocalDate segundaFeira = referencia.minusDays(diasParaSegunda);
                    inicio = segundaFeira.atStartOfDay();
                    fim = segundaFeira.plusDays(6).atTime(23, 59, 59);
                    break;
                case MES:
                    inicio = referencia.withDayOfMonth(1).atStartOfDay();
                    fim = referencia.withDayOfMonth(referencia.lengthOfMonth()).atTime(23, 59, 59);
                    break;
                default:
                    throw new IllegalArgumentException("Período não suportado: " + periodo);
            }

            logger.debug("Buscando consultas para usuário ID: {} no período de {} até {}", userId, inicio, fim);

            List<Consulta> consultas = consultaRepository
                    .findByAssistido_IdUsuarioOrVoluntario_IdUsuarioAndHorarioBetween(userId, userId, inicio, fim);

            logger.debug("Encontradas {} consultas para o usuário no período", consultas.size());

            List<Consulta> consultasOrdenadas = consultas.stream()
                    .sorted(Comparator.comparing(Consulta::getHorario).reversed())
                    .collect(Collectors.toList());

            return consultaUtil.mapConsultasToOutput(consultasOrdenadas);

        } catch (Exception e) {
            logger.error("Erro ao buscar consultas do usuário logado para período {}: {}", periodo, e.getMessage());
            throw new RuntimeException("Erro ao buscar consultas do usuário logado", e);
        }
    }
}