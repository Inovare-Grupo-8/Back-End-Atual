package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarTodasConsultasUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuscarTodasConsultasUseCaseImpl implements BuscarTodasConsultasUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarTodasConsultasUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<ConsultaOutput> buscarTodasConsultas() {
        logger.info("Buscando todas as consultas do sistema");

        try {
            List<Consulta> consultas = consultaRepository.findAll();
            logger.debug("Encontradas {} consultas no sistema", consultas.size());

            List<ConsultaOutput> consultasOutput = consultas.stream()
                    .sorted(Comparator.comparing(Consulta::getHorario).reversed())
                    .map(this::mapConsultaToOutput)
                    .collect(Collectors.toList());

            return consultasOutput;

        } catch (Exception e) {
            logger.error("Erro ao buscar todas as consultas: {}", e.getMessage());
            throw new RuntimeException("Erro ao buscar todas as consultas", e);
        }
    }

    private ConsultaOutput mapConsultaToOutput(Consulta consulta) {
        ConsultaOutput output = new ConsultaOutput();
        output.setIdConsulta(consulta.getIdConsulta());
        output.setHorario(consulta.getHorario());
        output.setStatus(consulta.getStatus().toString());
        output.setModalidade(consulta.getModalidade().toString());
        output.setLocal(consulta.getLocal());
        output.setObservacoes(consulta.getObservacoes());
        if (consulta.getEspecialidade() != null) {
            output.setIdEspecialidade(consulta.getEspecialidade().getIdEspecialidade());
            output.setNomeEspecialidade(consulta.getEspecialidade().getNome());
        }
        if (consulta.getVoluntario() != null) {
            output.setIdVoluntario(consulta.getVoluntario().getIdUsuario());
            if (consulta.getVoluntario().getFicha() != null) {
                output.setNomeVoluntario(consulta.getVoluntario().getFicha().getNome());
            }
        }
        if (consulta.getAssistido() != null) {
            output.setIdAssistido(consulta.getAssistido().getIdUsuario());
            if (consulta.getAssistido().getFicha() != null) {
                output.setNomeAssistido(consulta.getAssistido().getFicha().getNome());
            }
        }
        output.setFeedbackStatus(consulta.getFeedbackStatus());
        output.setAvaliacaoStatus(consulta.getAvaliacaoStatus());
        output.setCriadoEm(consulta.getCriadoEm());
        output.setAtualizadoEm(consulta.getAtualizadoEm());
        return output;
    }
}
