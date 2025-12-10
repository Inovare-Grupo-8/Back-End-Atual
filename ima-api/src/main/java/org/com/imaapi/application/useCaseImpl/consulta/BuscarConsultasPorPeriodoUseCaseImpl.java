package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultasPorPeriodoUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
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
public class BuscarConsultasPorPeriodoUseCaseImpl implements BuscarConsultasPorPeriodoUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultasPorPeriodoUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public List<ConsultaOutput> buscarPorDia(String user, LocalDate data) {
        logger.info("Buscando consultas do dia {} para usuário tipo: {}", data, user);

        try {
            consultaUtil.validarTipoUsuario(user);
            
            // Se data não informada, usa data atual
            LocalDate dataConsulta = data != null ? data : LocalDate.now();
            
            LocalDateTime inicio = dataConsulta.atStartOfDay();
            LocalDateTime fim = dataConsulta.atTime(23, 59, 59);

            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            Integer userId = usuarioLogado.getIdUsuario();

            logger.debug("Buscando consultas do dia para {} com ID: {}, período: {} até {}", 
                        user, userId, inicio, fim);

            List<Consulta> consultas = buscarConsultasPorPeriodo(userId, user, inicio, fim);
            
            // Ordenar por horário
            List<Consulta> consultasOrdenadas = consultas.stream()
                    .sorted(Comparator.comparing(Consulta::getHorario))
                    .collect(Collectors.toList());

            logger.debug("Encontradas {} consultas do dia {} para {} com ID: {}", 
                        consultasOrdenadas.size(), dataConsulta, user, userId);

            return consultaUtil.mapConsultasToOutput(consultasOrdenadas);

        } catch (Exception e) {
            logger.error("Erro ao buscar consultas do dia {} para usuário {}: {}", 
                        data, user, e.getMessage());
            throw new RuntimeException("Erro ao buscar consultas do dia", e);
        }
    }

    @Override
    public List<ConsultaOutput> buscarPorSemana(String user, LocalDate referencia) {
        logger.info("Buscando consultas da semana (referência: {}) para usuário tipo: {}", referencia, user);

        try {
            consultaUtil.validarTipoUsuario(user);
            
            // Se referência não informada, usa data atual
            LocalDate dataReferencia = referencia != null ? referencia : LocalDate.now();
            
            // Calcular início e fim da semana (segunda a domingo)
            DayOfWeek diaSemanaAtual = dataReferencia.getDayOfWeek();
            int diasParaSegunda = diaSemanaAtual.getValue() - DayOfWeek.MONDAY.getValue();
            
            LocalDateTime inicio = dataReferencia.minusDays(diasParaSegunda)
                    .atStartOfDay();
            LocalDateTime fim = inicio.plusDays(6)
                    .toLocalDate().atTime(23, 59, 59);

            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            Integer userId = usuarioLogado.getIdUsuario();

            logger.debug("Buscando consultas da semana para {} com ID: {}, período: {} até {}", 
                        user, userId, inicio, fim);

            List<Consulta> consultas = buscarConsultasPorPeriodo(userId, user, inicio, fim);
            
            // Ordenar por horário
            List<Consulta> consultasOrdenadas = consultas.stream()
                    .sorted(Comparator.comparing(Consulta::getHorario))
                    .collect(Collectors.toList());

            logger.debug("Encontradas {} consultas da semana para {} com ID: {}", 
                        consultasOrdenadas.size(), user, userId);

            return consultaUtil.mapConsultasToOutput(consultasOrdenadas);

        } catch (Exception e) {
            logger.error("Erro ao buscar consultas da semana (referência: {}) para usuário {}: {}", 
                        referencia, user, e.getMessage());
            throw new RuntimeException("Erro ao buscar consultas da semana", e);
        }
    }

    @Override
    public List<ConsultaOutput> buscarPorMes(String user, LocalDate referencia) {
        logger.info("Buscando consultas do mês (referência: {}) para usuário tipo: {}", referencia, user);

        try {
            consultaUtil.validarTipoUsuario(user);
            
            // Se referência não informada, usa data atual
            LocalDate dataReferencia = referencia != null ? referencia : LocalDate.now();
            
            // Calcular início e fim do mês
            LocalDateTime inicio = dataReferencia.withDayOfMonth(1).atStartOfDay();
            LocalDateTime fim = dataReferencia.withDayOfMonth(dataReferencia.lengthOfMonth())
                    .atTime(23, 59, 59);

            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            Integer userId = usuarioLogado.getIdUsuario();

            logger.debug("Buscando consultas do mês para {} com ID: {}, período: {} até {}", 
                        user, userId, inicio, fim);

            List<Consulta> consultas = buscarConsultasPorPeriodo(userId, user, inicio, fim);
            
            // Ordenar por horário (mais recentes primeiro para o mês)
            List<Consulta> consultasOrdenadas = consultas.stream()
                    .sorted(Comparator.comparing(Consulta::getHorario).reversed())
                    .collect(Collectors.toList());

            logger.debug("Encontradas {} consultas do mês para {} com ID: {}", 
                        consultasOrdenadas.size(), user, userId);

            return consultaUtil.mapConsultasToOutput(consultasOrdenadas);

        } catch (Exception e) {
            logger.error("Erro ao buscar consultas do mês (referência: {}) para usuário {}: {}", 
                        referencia, user, e.getMessage());
            throw new RuntimeException("Erro ao buscar consultas do mês", e);
        }
    }

    // Método auxiliar para buscar consultas por período
    private List<Consulta> buscarConsultasPorPeriodo(Integer userId, String user, 
                                                    LocalDateTime inicio, LocalDateTime fim) {
        if (user.equals("voluntario")) {
            return consultaRepository.findByVoluntario_IdUsuarioAndHorarioBetween(userId, inicio, fim);
        } else {
            return consultaRepository.findByAssistido_IdUsuarioAndHorarioBetween(userId, inicio, fim);
        }
    }
}