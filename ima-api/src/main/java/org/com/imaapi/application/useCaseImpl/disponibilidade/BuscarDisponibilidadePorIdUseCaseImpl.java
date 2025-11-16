package org.com.imaapi.application.useCaseImpl.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;
import org.com.imaapi.application.useCase.disponibilidade.BuscarDisponibilidadePorIdUseCase;
import org.com.imaapi.domain.model.Disponibilidade;
import org.com.imaapi.domain.repository.DisponibilidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscarDisponibilidadePorIdUseCaseImpl implements BuscarDisponibilidadePorIdUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarDisponibilidadePorIdUseCaseImpl.class);
    
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeUtil disponibilidadeUtil;
    
    public BuscarDisponibilidadePorIdUseCaseImpl(
            DisponibilidadeRepository disponibilidadeRepository,
            DisponibilidadeUtil disponibilidadeUtil) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.disponibilidadeUtil = disponibilidadeUtil;
    }
    
    @Override
    public DisponibilidadeOutput buscarDisponibilidadePorId(Integer id) {
        // Validação de entrada
        disponibilidadeUtil.validarId(id);
        
        LOGGER.info("Buscando disponibilidade com ID: {}", id);
        
        // Buscar no repository
        Disponibilidade disponibilidade = disponibilidadeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Disponibilidade não encontrada com ID: {}", id);
                    return new IllegalArgumentException("Disponibilidade não encontrada com ID: " + id);
                });
        
        LOGGER.info("Disponibilidade encontrada: ID={}, Data/Horário={}", 
                   disponibilidade.getIdDisponibilidade(), disponibilidade.getDataHorario());
        
        // Converter para output e retornar
        return disponibilidadeUtil.converterParaDisponibilidadeOutput(disponibilidade);
    }
}