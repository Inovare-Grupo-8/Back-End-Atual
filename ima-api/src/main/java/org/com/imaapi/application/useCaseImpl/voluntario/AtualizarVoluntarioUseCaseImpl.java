package org.com.imaapi.application.useCaseImpl.voluntario;

import org.com.imaapi.application.dto.usuario.input.VoluntarioInput;
import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;
import org.com.imaapi.application.useCase.voluntario.AtualizarVoluntarioUseCase;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarVoluntarioUseCaseImpl implements AtualizarVoluntarioUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarVoluntarioUseCaseImpl.class);
    
    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioUtil voluntarioUtil;
    
    public AtualizarVoluntarioUseCaseImpl(
            VoluntarioRepository voluntarioRepository,
            VoluntarioUtil voluntarioUtil) {
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioUtil = voluntarioUtil;
    }
    
    @Override
    @Transactional
    public VoluntarioOutput atualizarVoluntario(Integer usuarioId, VoluntarioInput voluntarioInput) {
        // Validações de entrada
        voluntarioUtil.validarId(usuarioId);
        voluntarioUtil.validarVoluntarioInput(voluntarioInput);
        
        LOGGER.info("Atualizando voluntário para usuário ID: {}", usuarioId);
        
        // Buscar voluntário existente por usuarioId
        Voluntario voluntarioExistente = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
        if (voluntarioExistente == null) {
            LOGGER.warn("Voluntário não encontrado para usuário ID: {}", usuarioId);
            throw new IllegalArgumentException("Voluntário não encontrado para usuário ID: " + usuarioId);
        }
        
        // Atualizar campos
        voluntarioExistente.setFuncao(voluntarioInput.getFuncao());
        
        // Salvar
        Voluntario voluntarioAtualizado = voluntarioRepository.save(voluntarioExistente);
        
        LOGGER.info("Voluntário atualizado com sucesso: ID={}, Nova função={}", 
                   voluntarioAtualizado.getIdVoluntario(), voluntarioAtualizado.getFuncao());
        
        // Converter para output e retornar
        return voluntarioUtil.converterParaVoluntarioOutput(voluntarioAtualizado);
    }
}