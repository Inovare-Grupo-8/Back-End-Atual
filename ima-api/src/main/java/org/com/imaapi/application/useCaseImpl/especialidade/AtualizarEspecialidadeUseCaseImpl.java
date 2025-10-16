package org.com.imaapi.application.useCaseImpl.especialidade;

import org.com.imaapi.application.dto.especialidade.input.EspecialidadeInput;
import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.application.useCase.especialidade.AtualizarEspecialidadeUseCase;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarEspecialidadeUseCaseImpl implements AtualizarEspecialidadeUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarEspecialidadeUseCaseImpl.class);
    
    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeUtil especialidadeUtil;
    
    public AtualizarEspecialidadeUseCaseImpl(
            EspecialidadeRepository especialidadeRepository, 
            EspecialidadeUtil especialidadeUtil) {
        this.especialidadeRepository = especialidadeRepository;
        this.especialidadeUtil = especialidadeUtil;
    }
    
    @Override
    @Transactional
    public EspecialidadeOutput atualizarEspecialidade(Integer id, EspecialidadeInput especialidadeInput) {
        // Validações de entrada
        especialidadeUtil.validarId(id);
        especialidadeUtil.validarEspecialidadeInput(especialidadeInput);
        
        String novoNome = especialidadeInput.getNome().trim();
        
        LOGGER.info("Atualizando especialidade ID={} para nome: {}", id, novoNome);
        
        // Buscar especialidade existente
        Especialidade especialidadeExistente = especialidadeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Tentativa de atualizar especialidade inexistente: ID={}", id);
                    return new IllegalArgumentException("Especialidade não encontrada com ID: " + id);
                });
        
        // Verificar se o novo nome já existe (se for diferente do atual)
        if (!especialidadeExistente.getNome().equals(novoNome) && 
            especialidadeRepository.existsByNome(novoNome)) {
            LOGGER.warn("Tentativa de atualizar para nome já existente: {}", novoNome);
            throw new IllegalArgumentException("Já existe uma especialidade com esse nome: " + novoNome);
        }
        
        // Atualizar e salvar
        especialidadeExistente.setNome(novoNome);
        Especialidade especialidadeAtualizada = especialidadeRepository.save(especialidadeExistente);
        
        LOGGER.info("Especialidade atualizada com sucesso: ID={}, Nome={}", 
                   especialidadeAtualizada.getIdEspecialidade(), especialidadeAtualizada.getNome());
        
        // Converter para output e retornar
        return especialidadeUtil.converterParaEspecialidadeOutput(especialidadeAtualizada);
    }
}