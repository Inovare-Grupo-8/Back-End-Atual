package org.com.imaapi.application.useCaseImpl.especialidade;

import org.com.imaapi.application.useCase.especialidade.DeletarEspecialidadeUseCase;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarEspecialidadeUseCaseImpl implements DeletarEspecialidadeUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletarEspecialidadeUseCaseImpl.class);
    
    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeUtil especialidadeUtil;
    
    public DeletarEspecialidadeUseCaseImpl(
            EspecialidadeRepository especialidadeRepository, 
            EspecialidadeUtil especialidadeUtil) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeUtil = especialidadeUtil;
    }
    
    @Override
    @Transactional
    public void deletarEspecialidade(Integer id) {
        // Validação de entrada
        especialidadeUtil.validarId(id);
        
        LOGGER.info("Deletando especialidade com ID: {}", id);
        
        // Verificar se existe
        if (!especialidadeRepository.existsById(id)) {
            LOGGER.warn("Tentativa de deletar especialidade inexistente: ID={}", id);
            throw new IllegalArgumentException("Especialidade não encontrada com ID: " + id);
        }
        
        // Deletar
        especialidadeRepository.deleteById(id);
        
        LOGGER.info("Especialidade deletada com sucesso: ID={}", id);
    }
}