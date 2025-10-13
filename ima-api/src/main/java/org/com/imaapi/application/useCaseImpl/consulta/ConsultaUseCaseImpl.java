package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.application.useCase.consulta.ConsultaUseCase;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConsultaUseCaseImpl implements ConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ConsultaUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public List<ConsultaOutput> buscarConsultasPorDia(String user, LocalDate data) {
        logger.info("Buscando consultas para o usuário {} na data {}", user, data);
        
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.atTime(LocalTime.MAX);
        
        List<Consulta> consultas;
        try {
            Integer userId = Integer.parseInt(user);
            consultas = consultaRepository.findByAssistido_IdUsuarioOrVoluntario_IdUsuarioAndHorarioBetween(
                    userId, userId, inicioDia, fimDia);
        } catch (NumberFormatException e) {
            logger.error("ID de usuário inválido: {}", user, e);
            return new ArrayList<>();
        }
        
        return mapToConsultaOutputList(consultas);
    }

    @Override
    public Page<ConsultaOutput> buscarConsultasPorDiaPaginado(String user, LocalDate data, Pageable pageable) {
        logger.info("Buscando consultas paginadas para o usuário {} na data {}", user, data);
        
        List<ConsultaOutput> consultas = buscarConsultasPorDia(user, data);
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), consultas.size());
        
        if (start > consultas.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, consultas.size());
        }
        
        return new PageImpl<>(consultas.subList(start, end), pageable, consultas.size());
    }

    @Override
    public Page<ConsultaOutput> buscarConsultasPorEspecialidade(Integer especialidadeId, Pageable pageable) {
        logger.info("Buscando consultas para a especialidade {}", especialidadeId);
        
        List<Consulta> todasConsultas = consultaRepository.findAll();
        List<Consulta> consultasFiltradas = todasConsultas.stream()
                .filter(c -> c.getEspecialidade() != null && 
                        c.getEspecialidade().getIdEspecialidade().equals(especialidadeId))
                .collect(Collectors.toList());
        
        List<ConsultaOutput> consultasOutput = mapToConsultaOutputList(consultasFiltradas);
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), consultasOutput.size());
        
        if (start > consultasOutput.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, consultasOutput.size());
        }
        
        return new PageImpl<>(consultasOutput.subList(start, end), pageable, consultasOutput.size());
    }

    @Override
    public List<ConsultaStatusCount> buscarEstatisticasConsultas() {
        logger.info("Buscando estatísticas de consultas");
        
        List<Consulta> todasConsultas = consultaRepository.findAll();
        Map<String, Long> contagem = new HashMap<>();
        
        // Inicializa contadores para todos os status
        for (StatusConsulta status : StatusConsulta.values()) {
            contagem.put(status.name(), 0L);
        }
        
        // Conta as consultas por status
        for (Consulta consulta : todasConsultas) {
            String status = consulta.getStatus().name();
            contagem.put(status, contagem.getOrDefault(status, 0L) + 1);
        }
        
        // Converte para a lista de resultado
        List<ConsultaStatusCount> resultado = new ArrayList<>();
        for (Map.Entry<String, Long> entry : contagem.entrySet()) {
            ConsultaStatusCount count = new ConsultaStatusCount();
            count.setStatus(entry.getKey());
            count.setCount(entry.getValue());
            resultado.add(count);
        }
        
        return resultado;
    }
    
    private List<ConsultaOutput> mapToConsultaOutputList(List<Consulta> consultas) {
        return consultas.stream().map(this::mapToConsultaOutput).collect(Collectors.toList());
    }
    
    private ConsultaOutput mapToConsultaOutput(Consulta consulta) {
        ConsultaOutput output = new ConsultaOutput();
        output.setIdConsulta(consulta.getIdConsulta());
        output.setHorario(consulta.getHorario());
        output.setStatus(consulta.getStatus().name());
        output.setModalidade(consulta.getModalidade().name());
        output.setLocal(consulta.getLocal());
        output.setObservacoes(consulta.getObservacoes());
        output.setFeedbackStatus(consulta.getFeedbackStatus());
        output.setAvaliacaoStatus(consulta.getAvaliacaoStatus());
        
        if (consulta.getEspecialidade() != null) {
            output.setIdEspecialidade(consulta.getEspecialidade().getIdEspecialidade());
            output.setNomeEspecialidade(consulta.getEspecialidade().getNome());
            output.setEspecialidade(consulta.getEspecialidade());
        }
        
        if (consulta.getVoluntario() != null) {
            output.setIdVoluntario(consulta.getVoluntario().getIdUsuario());
            output.setNomeVoluntario(consulta.getVoluntario().getFicha() != null ? consulta.getVoluntario().getFicha().getNome() : "");
            output.setIdEspecialista(consulta.getVoluntario().getIdUsuario());
            output.setNomeEspecialista(consulta.getVoluntario().getFicha() != null ? consulta.getVoluntario().getFicha().getNome() : "");
            output.setVoluntario(consulta.getVoluntario());
        }
        
        if (consulta.getAssistido() != null) {
            output.setIdAssistido(consulta.getAssistido().getIdUsuario());
            output.setNomeAssistido(consulta.getAssistido().getFicha() != null ? consulta.getAssistido().getFicha().getNome() : "");
            output.setIdCliente(consulta.getAssistido().getIdUsuario());
            output.setNomeCliente(consulta.getAssistido().getFicha() != null ? consulta.getAssistido().getFicha().getNome() : "");
            output.setAssistido(consulta.getAssistido());
        }
        
        return output;
    }
}