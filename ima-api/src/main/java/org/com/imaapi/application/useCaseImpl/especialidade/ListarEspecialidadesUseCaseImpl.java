package org.com.imaapi.application.useCaseImpl.especialidade;

import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.application.useCase.especialidade.ListarEspecialidadesUseCase;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarEspecialidadesUseCaseImpl implements ListarEspecialidadesUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ListarEspecialidadesUseCaseImpl.class);
    
    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeUtil especialidadeUtil;
    
    public ListarEspecialidadesUseCaseImpl(
            EspecialidadeRepository especialidadeRepository, 
            EspecialidadeUtil especialidadeUtil) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeUtil = especialidadeUtil;
    }
    
    @Override
    public List<EspecialidadeOutput> listarEspecialidades() {
        LOGGER.info("Listando todas as especialidades cadastradas");
        
        // Buscar todas as especialidades
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        
        LOGGER.info("Encontradas {} especialidades cadastradas", especialidades.size());
        
        // Converter para output e retornar
        return especialidades.stream()
                .map(especialidadeUtil::converterParaEspecialidadeOutput)
                .collect(Collectors.toList());
    }
}