package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.AdicionarFeedbackConsultaUseCase;
import org.com.imaapi.application.dto.consulta.input.ConsultaFeedbackInput;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdicionarFeedbackConsultaUseCaseImpl implements AdicionarFeedbackConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AdicionarFeedbackConsultaUseCaseImpl.class);

    private final ConsultaRepository consultaRepository;

    @Autowired
    public AdicionarFeedbackConsultaUseCaseImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void adicionarFeedback(Integer consultaId, ConsultaFeedbackInput input) {
        // Implementação da lógica de feedback
        logger.info("Adicionando feedback à consulta {}", consultaId);
        // ...
    }
}
