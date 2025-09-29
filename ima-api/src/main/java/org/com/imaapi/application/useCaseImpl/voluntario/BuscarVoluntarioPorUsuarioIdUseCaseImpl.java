package org.com.imaapi.application.useCaseImpl.voluntario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;
import org.com.imaapi.application.useCase.voluntario.BuscarVoluntarioPorUsuarioIdUseCase;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscarVoluntarioPorUsuarioIdUseCaseImpl implements BuscarVoluntarioPorUsuarioIdUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarVoluntarioPorUsuarioIdUseCaseImpl.class);
    
    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioUtil voluntarioUtil;
    
    public BuscarVoluntarioPorUsuarioIdUseCaseImpl(
            VoluntarioRepository voluntarioRepository,
            VoluntarioUtil voluntarioUtil) {
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioUtil = voluntarioUtil;
    }
    
    @Override
    public VoluntarioOutput buscarVoluntarioPorUsuarioId(Integer usuarioId) {
        // Validação de entrada
        voluntarioUtil.validarId(usuarioId);
        
        LOGGER.info("Buscando voluntário por usuário ID: {}", usuarioId);
        
        // Buscar no repository
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
        if (voluntario == null) {
            LOGGER.warn("Voluntário não encontrado para usuário ID: {}", usuarioId);
            throw new IllegalArgumentException("Voluntário não encontrado para usuário ID: " + usuarioId);
        }
        
        LOGGER.info("Voluntário encontrado: ID={}, Usuário ID={}", 
                   voluntario.getIdVoluntario(), usuarioId);
        
        // Converter para output e retornar
        return voluntarioUtil.converterParaVoluntarioOutput(voluntario);
    }
}