package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.RemarcarConsultaUseCase;
import org.com.imaapi.application.dto.consulta.input.ConsultaRemarcarInput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Consulta não encontrada com ID: {}", id);
                    return new RuntimeException("Consulta não encontrada");
                });

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

        consultaRepository.save(consulta);
        logger.info("Consulta remarcada com sucesso para {}", input.getNovoHorario());
    }
}
