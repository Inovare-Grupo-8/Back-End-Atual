package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultaPorIdUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarConsultaPorIdUseCaseImpl implements BuscarConsultaPorIdUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultaPorIdUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public ConsultaSimpleOutput buscarConsultaPorId(Integer id) {
        logger.info("Buscando consulta por ID: {}", id);

        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID da consulta deve ser um número positivo");
            }

            logger.debug("Executando busca no repositório para ID: {}", id);
            
            Consulta consulta = consultaRepository.findById(id).orElse(null);

            if (consulta == null) {
                logger.warn("Consulta não encontrada com ID: {}", id);
                return null;
            }

            logger.info("Consulta encontrada com ID: {}, convertendo para output", id);
            
            // Mapeamento direto para o DTO simples
            ConsultaSimpleOutput output = new ConsultaSimpleOutput();
            output.setIdConsulta(consulta.getIdConsulta());
            output.setHorario(consulta.getHorario());
            output.setStatus(consulta.getStatus() != null ? consulta.getStatus().toString() : "");
            output.setModalidade(consulta.getModalidade() != null ? consulta.getModalidade().toString() : "");
            output.setLocal(consulta.getLocal());
            output.setObservacoes(consulta.getObservacoes());
            output.setFeedbackStatus(consulta.getFeedbackStatus());
            output.setAvaliacaoStatus(consulta.getAvaliacaoStatus());
            output.setCriadoEm(consulta.getCriadoEm());
            output.setAtualizadoEm(consulta.getAtualizadoEm());
            
            logger.info("Consulta ID: {} convertida com sucesso", id);
            return output;

        } catch (Exception e) {
            logger.error("Erro ao buscar consulta por ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar consulta por ID: " + e.getMessage(), e);
        }
    }
}