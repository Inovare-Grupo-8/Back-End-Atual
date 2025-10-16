package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.RemarcarConsultaUseCase;
import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RemarcarConsultaUseCaseImpl implements RemarcarConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(RemarcarConsultaUseCaseImpl.class);

    private final ConsultaRepository consultaRepository;

    @Autowired
    public RemarcarConsultaUseCaseImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput remarcarConsulta(Integer id, ConsultaRemarcarInput input) {
        logger.info("Remarcando consulta com ID {} para um novo horário {}", id, input.getNovoHorario());

        try {
            // Validações
            if (id == null || input == null || input.getNovoHorario() == null) {
                logger.error("ID da consulta e novo horário são obrigatórios");
                throw new IllegalArgumentException("ID da consulta e novo horário são obrigatórios");
            }

            // Busca a entidade do domínio
            Consulta consulta = consultaRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Consulta não encontrada com ID: {}", id);
                        return new RuntimeException("Consulta não encontrada");
                    });

            // validações de negócio antes de atualizar
            if (consulta.getStatus() == StatusConsulta.CANCELADA ||
                    consulta.getStatus() == StatusConsulta.REALIZADA) {
                logger.error("Tentativa de remarcar consulta com status {}", consulta.getStatus());
                throw new IllegalStateException("Não é possível remarcar uma consulta " + consulta.getStatus());
            }

            if (input.getNovoHorario().isBefore(LocalDateTime.now())) {
                logger.error("Nova data deve ser futura: {}", input.getNovoHorario());
                throw new IllegalArgumentException("A nova data da consulta deve ser futura");
            }

            // Atualiza a entidade com os dados do DTO
            consulta.setHorario(input.getNovoHorario());
            
            if (input.getModalidade() != null) {
                consulta.setModalidade(input.getModalidade());
            }
            
            if (input.getLocal() != null && !input.getLocal().isEmpty()) {
                consulta.setLocal(input.getLocal());
            }
            
            if (input.getObservacoes() != null && !input.getObservacoes().isEmpty()) {
                consulta.setObservacoes(input.getObservacoes());
            }
            
            consulta.setStatus(StatusConsulta.REAGENDADA);

            // Salva a entidade atualizada
            Consulta consultaRemarcada = consultaRepository.save(consulta);
            logger.info("Consulta remarcada com sucesso para {}", input.getNovoHorario());

            // Retornar o output
            return consultaUtil.mapConsultaToOutput(consultaRemarcada);

        } catch (Exception e) {
            logger.error("Erro ao remarcar consulta com ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Erro ao remarcar consulta", e);
        }
    }
}