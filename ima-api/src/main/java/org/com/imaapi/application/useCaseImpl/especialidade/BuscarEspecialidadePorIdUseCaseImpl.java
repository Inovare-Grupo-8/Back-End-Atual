package org.com.imaapi.application.useCaseImpl.especialidade;

import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.application.useCase.especialidade.BuscarEspecialidadePorIdUseCase;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscarEspecialidadePorIdUseCaseImpl implements BuscarEspecialidadePorIdUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarEspecialidadePorIdUseCaseImpl.class);
    
    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeUtil especialidadeUtil;
    
    public BuscarEspecialidadePorIdUseCaseImpl(
            EspecialidadeRepository especialidadeRepository, 
            EspecialidadeUtil especialidadeUtil) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeUtil = especialidadeUtil;
    }
    
    @Override
    public EspecialidadeOutput buscarEspecialidadePorId(Integer id) {
        // Validação de entrada
        especialidadeUtil.validarId(id);
        
        LOGGER.info("Buscando especialidade com ID: {}", id);
        
        // Buscar no repository
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Especialidade não encontrada com ID: {}", id);
                    return new IllegalArgumentException("Especialidade não encontrada com ID: " + id);
                });
        
        LOGGER.info("Especialidade encontrada: ID={}, Nome={}", 
                   especialidade.getIdEspecialidade(), especialidade.getNome());
        
        // Converter para output e retornar
        return especialidadeUtil.converterParaEspecialidadeOutput(especialidade);
    }
}