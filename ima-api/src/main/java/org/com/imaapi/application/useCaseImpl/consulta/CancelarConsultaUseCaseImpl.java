package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.CancelarConsultaUseCase;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelarConsultaUseCaseImpl implements CancelarConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CancelarConsultaUseCaseImpl.class);

    private final ConsultaRepository consultaRepository;

    @Autowired
    public CancelarConsultaUseCaseImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void cancelarConsulta(Integer consultaId) {
        logger.info("Cancelando consulta {}", consultaId);
        // ...
    }
}
