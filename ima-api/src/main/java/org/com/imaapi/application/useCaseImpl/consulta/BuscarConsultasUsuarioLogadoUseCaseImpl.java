package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultasUsuarioLogadoUseCase;
import org.com.imaapi.application.dto.consulta.input.BuscarConsultasInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;
import org.com.imaapi.domain.model.Consulta;
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
    public List<ConsultaSimpleOutput> buscarConsultasDoUsuario(BuscarConsultasInput input) {
        logger.info("Executando busca de consultas para usuário ID: {}", input.getUserId());

        // Validações de negócio
        validarInput(input);

        // Aplicar regras de negócio
        Integer userId = obterUserIdValido(input.getUserId());
        Periodo periodo = converterPeriodo(input.getPeriodo());
        LocalDate dataReferencia = obterDataReferencia(input.getDataReferencia());

        // Calcular intervalo de tempo
        IntervaloTempo intervalo = calcularIntervalo(periodo, dataReferencia);
        
        logger.debug("Buscando consultas para usuário ID: {} no período de {} até {}", 
                    userId, intervalo.inicio, intervalo.fim);

        // Buscar consultas no repositório
        List<Consulta> consultas = consultaRepository
                .findByAssistido_IdUsuarioOrVoluntario_IdUsuarioAndHorarioBetween(
                    userId, userId, intervalo.inicio, intervalo.fim);

        logger.info("Encontradas {} consultas para o usuário ID: {}", consultas.size(), userId);

        // Ordenar consultas
        List<Consulta> consultasOrdenadas = consultas.stream()
                .sorted(Comparator.comparing(Consulta::getHorario).reversed())
                .collect(Collectors.toList());

        // Mapear para DTO de output
        return consultaUtil.mapConsultasToSimpleOutput(consultasOrdenadas);
    }

    private void validarInput(BuscarConsultasInput input) {
        if (input == null) {
            throw new IllegalArgumentException("Input não pode ser nulo");
        }
        if (input.getPeriodo() == null || input.getPeriodo().trim().isEmpty()) {
            throw new IllegalArgumentException("Período é obrigatório");
        }
    }

    private Integer obterUserIdValido(Integer userId) {
        return (userId != null && userId > 0) ? userId : 1;
    }

    private Periodo converterPeriodo(String periodoStr) {
        try {
            return Periodo.valueOf(periodoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Período inválido fornecido: {}. Usando ATUAL como padrão", periodoStr);
            return Periodo.ATUAL;
        }
    }

    private LocalDate obterDataReferencia(LocalDate dataReferencia) {
        return (dataReferencia != null) ? dataReferencia : LocalDate.now();
    }

    private IntervaloTempo calcularIntervalo(Periodo periodo, LocalDate referencia) {
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
            case ATUAL:
            default:
                inicio = LocalDateTime.now();
                fim = referencia.plusMonths(6).atTime(23, 59, 59);
                break;
        }

        return new IntervaloTempo(inicio, fim);
    }

    private static class IntervaloTempo {
        final LocalDateTime inicio;
        final LocalDateTime fim;

        IntervaloTempo(LocalDateTime inicio, LocalDateTime fim) {
            this.inicio = inicio;
            this.fim = fim;
        }
    }
}