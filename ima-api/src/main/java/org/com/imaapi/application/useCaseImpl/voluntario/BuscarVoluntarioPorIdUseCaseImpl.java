package org.com.imaapi.application.useCaseImpl.voluntario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;
import org.com.imaapi.application.useCase.voluntario.BuscarVoluntarioPorIdUseCase;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscarVoluntarioPorIdUseCaseImpl implements BuscarVoluntarioPorIdUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarVoluntarioPorIdUseCaseImpl.class);
    
    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioUtil voluntarioUtil;
    
    public BuscarVoluntarioPorIdUseCaseImpl(
            VoluntarioRepository voluntarioRepository,
            VoluntarioUtil voluntarioUtil) {
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioUtil = voluntarioUtil;
    }
    
    @Override
    public VoluntarioOutput buscarVoluntarioPorId(Integer id) {
        // Validação de entrada
        voluntarioUtil.validarId(id);
        
        LOGGER.info("Buscando voluntário com ID: {}", id);
        
        // Buscar no repository
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Voluntário não encontrado com ID: {}", id);
                    return new IllegalArgumentException("Voluntário não encontrado com ID: " + id);
                });
        
        LOGGER.info("Voluntário encontrado: ID={}, Função={}", 
                   voluntario.getIdVoluntario(), voluntario.getFuncao());
        
        // Converter para output e retornar
        return voluntarioUtil.converterParaVoluntarioOutput(voluntario);
    }
}