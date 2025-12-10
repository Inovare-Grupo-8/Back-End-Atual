package org.com.imaapi.application.useCaseImpl.disponibilidade;

import org.com.imaapi.application.useCase.disponibilidade.DeletarDisponibilidadeUseCase;
import org.com.imaapi.domain.repository.DisponibilidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarDisponibilidadeUseCaseImpl implements DeletarDisponibilidadeUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletarDisponibilidadeUseCaseImpl.class);
    
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeUtil disponibilidadeUtil;
    
    public DeletarDisponibilidadeUseCaseImpl(
            DisponibilidadeRepository disponibilidadeRepository,
            DisponibilidadeUtil disponibilidadeUtil) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.disponibilidadeUtil = disponibilidadeUtil;
    }
    
    @Override
    @Transactional
    public void deletarDisponibilidade(Integer id) {
        // Validação de entrada
        disponibilidadeUtil.validarId(id);
        
        LOGGER.info("Deletando disponibilidade com ID: {}", id);
        
        // Verificar se existe
        if (!disponibilidadeRepository.existsById(id)) {
            LOGGER.warn("Tentativa de deletar disponibilidade inexistente: ID={}", id);
            throw new IllegalArgumentException("Disponibilidade não encontrada com ID: " + id);
        }
        
        // Deletar
        disponibilidadeRepository.deleteById(id);
        
        LOGGER.info("Disponibilidade deletada com sucesso: ID={}", id);
    }
}