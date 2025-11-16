package org.com.imaapi.application.useCaseImpl.especialidade;

import org.com.imaapi.application.dto.especialidade.input.EspecialidadeInput;
import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.application.useCase.especialidade.CriarEspecialidadeUseCase;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarEspecialidadeUseCaseImpl implements CriarEspecialidadeUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CriarEspecialidadeUseCaseImpl.class);
    
    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeUtil especialidadeUtil;
    
    public CriarEspecialidadeUseCaseImpl(
            EspecialidadeRepository especialidadeRepository, 
            EspecialidadeUtil especialidadeUtil) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeUtil = especialidadeUtil;
    }
    
    @Override
    @Transactional
    public EspecialidadeOutput criarEspecialidade(EspecialidadeInput especialidadeInput) {
        // Validações de entrada
        especialidadeUtil.validarEspecialidadeInput(especialidadeInput);
        
        String nomeEspecialidade = especialidadeInput.getNome().trim();
        
        LOGGER.info("Criando nova especialidade: {}", nomeEspecialidade);
        
        // Verificar se já existe
        if (especialidadeRepository.existsByNome(nomeEspecialidade)) {
            LOGGER.warn("Tentativa de criar especialidade já existente: {}", nomeEspecialidade);
            throw new IllegalArgumentException("Já existe uma especialidade com esse nome: " + nomeEspecialidade);
        }
        
        // Converter para entidade e salvar
        Especialidade especialidade = especialidadeUtil.converterParaEspecialidade(especialidadeInput);
        Especialidade especialidadeSalva = especialidadeRepository.save(especialidade);
        
        LOGGER.info("Especialidade criada com sucesso: ID={}, Nome={}", 
                   especialidadeSalva.getIdEspecialidade(), especialidadeSalva.getNome());
        
        // Converter para output e retornar
        return especialidadeUtil.converterParaEspecialidadeOutput(especialidadeSalva);
    }
}