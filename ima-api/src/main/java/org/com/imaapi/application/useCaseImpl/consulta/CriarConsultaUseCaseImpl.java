package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.CriarConsultaUseCase;
import org.com.imaapi.application.dto.consulta.input.ConsultaInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarConsultaUseCaseImpl implements CriarConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CriarConsultaUseCaseImpl.class);

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadeRepository especialidadeRepository;

    @Autowired
    public CriarConsultaUseCaseImpl(ConsultaRepository consultaRepository,
                                    UsuarioRepository usuarioRepository,
                                    EspecialidadeRepository especialidadeRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @Override
    public ConsultaOutput criarConsulta(ConsultaInput input) {
        logger.info("Criando consulta para input: {}", input);
        return null;
    }
}
