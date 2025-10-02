package org.com.imaapi.application.useCaseImpl.voluntario;

import org.com.imaapi.application.useCase.voluntario.ExcluirVoluntarioUseCase;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExcluirVoluntarioUseCaseImpl implements ExcluirVoluntarioUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcluirVoluntarioUseCaseImpl.class);
    
    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioUtil voluntarioUtil;
    
    public ExcluirVoluntarioUseCaseImpl(
            VoluntarioRepository voluntarioRepository,
            VoluntarioUtil voluntarioUtil) {
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioUtil = voluntarioUtil;
    }
    
    @Override
    @Transactional
    public void excluirVoluntario(Integer usuarioId) {
        // Validação de entrada
        voluntarioUtil.validarId(usuarioId);
        
        LOGGER.info("Excluindo voluntário para usuário ID: {}", usuarioId);
        
        // Buscar voluntário por usuarioId
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
        if (voluntario == null) {
            LOGGER.warn("Voluntário não encontrado para usuário ID: {}, possível deleção em cascata anterior", usuarioId);
            return; // Não lançar erro, apenas log de warning
        }
        
        // Excluir por ID do voluntário
        voluntarioRepository.deleteById(voluntario.getIdVoluntario());
        
        LOGGER.info("Voluntário excluído com sucesso: ID={}, Usuário ID={}", 
                   voluntario.getIdVoluntario(), usuarioId);
    }
}