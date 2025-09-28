package org.com.imaapi.application.useCaseImpl.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.input.DisponibilidadeInput;
import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;
import org.com.imaapi.application.useCase.disponibilidade.AtualizarDisponibilidadeUseCase;
import org.com.imaapi.domain.model.Disponibilidade;
import org.com.imaapi.domain.repository.DisponibilidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarDisponibilidadeUseCaseImpl implements AtualizarDisponibilidadeUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarDisponibilidadeUseCaseImpl.class);
    
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeUtil disponibilidadeUtil;
    
    public AtualizarDisponibilidadeUseCaseImpl(
            DisponibilidadeRepository disponibilidadeRepository,
            DisponibilidadeUtil disponibilidadeUtil) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.disponibilidadeUtil = disponibilidadeUtil;
    }
    
    @Override
    @Transactional
    public DisponibilidadeOutput atualizarDisponibilidade(Integer id, DisponibilidadeInput disponibilidadeInput) {
        // Validações de entrada
        disponibilidadeUtil.validarId(id);
        disponibilidadeUtil.validarDisponibilidadeInput(disponibilidadeInput);
        
        LOGGER.info("Atualizando disponibilidade ID={} para nova data/horário: {}", 
                   id, disponibilidadeInput.getDataHorario());
        
        // Buscar disponibilidade existente
        Disponibilidade disponibilidadeExistente = disponibilidadeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Tentativa de atualizar disponibilidade inexistente: ID={}", id);
                    return new IllegalArgumentException("Disponibilidade não encontrada com ID: " + id);
                });
        
        // Atualizar dados
        disponibilidadeExistente.setDataHorario(disponibilidadeInput.getDataHorario());
        
        // Salvar
        Disponibilidade disponibilidadeAtualizada = disponibilidadeRepository.save(disponibilidadeExistente);
        
        LOGGER.info("Disponibilidade atualizada com sucesso: ID={}, Nova data/horário={}", 
                   disponibilidadeAtualizada.getIdDisponibilidade(), disponibilidadeAtualizada.getDataHorario());
        
        // Converter para output e retornar
        return disponibilidadeUtil.converterParaDisponibilidadeOutput(disponibilidadeAtualizada);
    }
}