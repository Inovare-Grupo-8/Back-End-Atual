package org.com.imaapi.application.useCaseImpl.voluntario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;
import org.com.imaapi.application.useCase.voluntario.ListarVoluntariosUseCase;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarVoluntariosUseCaseImpl implements ListarVoluntariosUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ListarVoluntariosUseCaseImpl.class);
    
    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioUtil voluntarioUtil;
    
    public ListarVoluntariosUseCaseImpl(
            VoluntarioRepository voluntarioRepository,
            VoluntarioUtil voluntarioUtil) {
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioUtil = voluntarioUtil;
    }
    
    @Override
    public List<VoluntarioOutput> listarVoluntarios() {
        LOGGER.info("Listando todos os voluntários cadastrados");
        
        // Buscar todos os voluntários
        List<Voluntario> voluntarios = voluntarioRepository.findAll();
        
        LOGGER.info("Encontrados {} voluntários cadastrados", voluntarios.size());
        
        // Converter para output e retornar
        return voluntarios.stream()
                .map(voluntarioUtil::converterParaVoluntarioOutput)
                .collect(Collectors.toList());
    }
}