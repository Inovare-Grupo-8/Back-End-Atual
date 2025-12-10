package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.CancelarConsultaUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CancelarConsultaUseCaseImpl implements CancelarConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CancelarConsultaUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput cancelarConsulta(Integer consultaId) {
        logger.info("Cancelando consulta com ID {}", consultaId);

        try {
            if (consultaId == null) {
                logger.error("ID da consulta é obrigatório");
                throw new IllegalArgumentException("ID da consulta é obrigatório");
            }

            // Buscar a consulta pelo ID
            Consulta consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> {
                        logger.error("Consulta não encontrada com ID: {}", consultaId);
                        return new RuntimeException("Consulta não encontrada");
                    });

            // Verificar se a consulta pode ser cancelada (não está já cancelada ou realizada)
            if (consulta.getStatus() == StatusConsulta.CANCELADA) {
                logger.warn("Tentativa de cancelar consulta já cancelada com ID: {}", consultaId);
                throw new IllegalStateException("Consulta já está cancelada");
            }

            if (consulta.getStatus() == StatusConsulta.REALIZADA) {
                logger.warn("Tentativa de cancelar consulta já realizada com ID: {}", consultaId);
                throw new IllegalStateException("Não é possível cancelar consulta já realizada");
            }

            // Atualizar o status da consulta para CANCELADA
            logger.info("Cancelando consulta ID: {}", consultaId);
            consulta.setStatus(StatusConsulta.CANCELADA);

            // Salvar a consulta atualizada
            Consulta consultaCancelada = consultaRepository.save(consulta);
            logger.info("Consulta cancelada com sucesso. ID: {}", consultaId);

            // Retornar o output usando o ConsultaUtil
            List<ConsultaOutput> outputs = consultaUtil.mapConsultasToOutput(Collections.singletonList(consultaCancelada));
            return outputs.isEmpty() ? null : outputs.get(0);

        } catch (Exception e) {
            logger.error("Erro ao cancelar consulta com ID {}: {}", consultaId, e.getMessage());
            throw new RuntimeException("Erro ao cancelar consulta", e);
        }
    }
}
