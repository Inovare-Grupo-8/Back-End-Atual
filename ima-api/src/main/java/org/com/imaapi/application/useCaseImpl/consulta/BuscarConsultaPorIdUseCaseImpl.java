package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultaPorIdUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BuscarConsultaPorIdUseCaseImpl implements BuscarConsultaPorIdUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultaPorIdUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput buscarConsultaPorId(Integer id) {
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
            
            // Usar o ConsultaUtil para mapear corretamente
            List<ConsultaOutput> outputs = consultaUtil.mapConsultasToOutput(Collections.singletonList(consulta));

            logger.info("Consulta ID: {} convertida com sucesso", id);
            return outputs.isEmpty() ? null : outputs.get(0);

        } catch (Exception e) {
            logger.error("Erro ao buscar consulta por ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar consulta por ID: " + e.getMessage(), e);
        }
    }
}