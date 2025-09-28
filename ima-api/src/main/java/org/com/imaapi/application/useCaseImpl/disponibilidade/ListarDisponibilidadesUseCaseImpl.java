package org.com.imaapi.application.useCaseImpl.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;
import org.com.imaapi.application.useCase.disponibilidade.ListarDisponibilidadesUseCase;
import org.com.imaapi.domain.model.Disponibilidade;
import org.com.imaapi.domain.repository.DisponibilidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarDisponibilidadesUseCaseImpl implements ListarDisponibilidadesUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ListarDisponibilidadesUseCaseImpl.class);
    
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final DisponibilidadeUtil disponibilidadeUtil;
    
    public ListarDisponibilidadesUseCaseImpl(
            DisponibilidadeRepository disponibilidadeRepository,
            DisponibilidadeUtil disponibilidadeUtil) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.disponibilidadeUtil = disponibilidadeUtil;
    }
    
    @Override
    public List<DisponibilidadeOutput> listarDisponibilidades() {
        LOGGER.info("Listando todas as disponibilidades cadastradas");
        
        // Buscar todas as disponibilidades
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findAll();
        
        LOGGER.info("Encontradas {} disponibilidades cadastradas", disponibilidades.size());
        
        // Converter para output e retornar
        return disponibilidades.stream()
                .map(disponibilidadeUtil::converterParaDisponibilidadeOutput)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DisponibilidadeOutput> listarDisponibilidadesPorVoluntario(Integer voluntarioId) {
        // Validar ID
        disponibilidadeUtil.validarId(voluntarioId);
        
        LOGGER.info("Listando disponibilidades para voluntário ID: {}", voluntarioId);
        
        // Buscar todas as disponibilidades e filtrar por voluntário
        // (Usando filtro na aplicação já que não podemos modificar o repository)
        List<Disponibilidade> todasDisponibilidades = disponibilidadeRepository.findAll();
        
        List<Disponibilidade> disponibilidadesDoVoluntario = todasDisponibilidades.stream()
                .filter(disp -> disp.getVoluntario() != null && 
                               disp.getVoluntario().getIdVoluntario().equals(voluntarioId))
                .collect(Collectors.toList());
        
        LOGGER.info("Encontradas {} disponibilidades para voluntário ID: {}", 
                   disponibilidadesDoVoluntario.size(), voluntarioId);
        
        // Converter para output e retornar
        return disponibilidadesDoVoluntario.stream()
                .map(disponibilidadeUtil::converterParaDisponibilidadeOutput)
                .collect(Collectors.toList());
    }
}