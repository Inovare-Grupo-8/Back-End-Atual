package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultasDoUsuarioUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarConsultasDoUsuarioUseCaseImpl implements BuscarConsultasDoUsuarioUseCase {
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultasDoUsuarioUseCaseImpl.class);

    private final ConsultaRepository consultaRepository;

    @Autowired
    public BuscarConsultasDoUsuarioUseCaseImpl(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public List<ConsultaOutput> buscarConsultasDoUsuario(Integer usuarioId) {
        logger.info("Buscando consultas do usuário {}", usuarioId);
        // ...
        return null;
    }
}
