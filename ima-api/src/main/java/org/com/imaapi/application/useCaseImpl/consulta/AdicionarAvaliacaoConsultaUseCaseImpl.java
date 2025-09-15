package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.AdicionarAvaliacaoConsultaUseCase;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdicionarAvaliacaoConsultaUseCaseImpl implements AdicionarAvaliacaoConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AdicionarAvaliacaoConsultaUseCaseImpl.class);

    private final ConsultaRepository consultaRepository;

    @Autowired
    public AdicionarAvaliacaoConsultaUseCaseImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void adicionarAvaliacao(Integer consultaId, ConsultaAvaliacaoInput input) {
        // Implementação da lógica de avaliação
        logger.info("Adicionando avaliação à consulta {}", consultaId);
        // ...
    }
}
