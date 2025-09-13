package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.dto.consulta.input.ConsultaInput;
import org.com.imaapi.application.useCase.consulta.RemarcarConsultaUseCase;
import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;
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

    @Override
    public void remarcarConsulta(Integer id, ConsultaRemarcarInput input) {
        logger.info("Remarcando consulta com ID {} para um novo horário {}", id, input.getNovoHorario());

        // Busca a entidade do domínio
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Consulta não encontrada com ID: {}", id);
                    return new RuntimeException("Consulta não encontrada");
                });

        // validações de negócio antes de atualizar
        if (consulta.getStatus() == StatusConsulta.CANCELADA ||
                consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new RuntimeException("Não é possível remarcar uma consulta " + consulta.getStatus());
        }

        if (input.getNovoHorario().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("A nova data da consulta deve ser futura");
        }

        // Atualiza a entidade com os dados do DTO
        consulta.setHorario(input.getNovoHorario());
        consulta.setModalidade(input.getModalidade());
        consulta.setLocal(input.getLocal());
        consulta.setObservacoes(input.getObservacoes());
        consulta.setStatus(StatusConsulta.REAGENDADA);

        // Salva a entidade atualizada
        consultaRepository.save(consulta);
        logger.info("Consulta remarcada com sucesso para {}", input.getNovoHorario());
    }
}