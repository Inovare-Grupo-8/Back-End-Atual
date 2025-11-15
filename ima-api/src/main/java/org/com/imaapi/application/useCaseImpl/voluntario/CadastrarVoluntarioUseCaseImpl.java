package org.com.imaapi.application.useCaseImpl.voluntario;

import org.com.imaapi.application.dto.usuario.input.VoluntarioInput;
import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;
import org.com.imaapi.application.useCase.voluntario.CadastrarVoluntarioUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarVoluntarioUseCaseImpl implements CadastrarVoluntarioUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CadastrarVoluntarioUseCaseImpl.class);
    
    private final VoluntarioRepository voluntarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final VoluntarioUtil voluntarioUtil;
    
    public CadastrarVoluntarioUseCaseImpl(
            VoluntarioRepository voluntarioRepository,
            UsuarioRepository usuarioRepository,
            VoluntarioUtil voluntarioUtil) {
        this.voluntarioRepository = voluntarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.voluntarioUtil = voluntarioUtil;
    }
    
    @Override
    @Transactional
    public VoluntarioOutput cadastrarVoluntario(VoluntarioInput voluntarioInput) {
        // Validações de entrada
        voluntarioUtil.validarVoluntarioInput(voluntarioInput);
        
        LOGGER.info("Cadastrando voluntário para usuário ID: {}", voluntarioInput.getFkUsuario());
        
        // Verificar se já existe um voluntário para este usuário
        Voluntario voluntarioExistente = voluntarioRepository.findByUsuario_IdUsuario(voluntarioInput.getFkUsuario());
        if (voluntarioExistente != null) {
            LOGGER.info("Voluntário já existe para usuário ID: {}, atualizando dados...", voluntarioInput.getFkUsuario());
            
            // Atualizar dados do voluntário existente
            voluntarioExistente.setFuncao(voluntarioInput.getFuncao());
            Voluntario voluntarioAtualizado = voluntarioRepository.save(voluntarioExistente);
            
            LOGGER.info("Voluntário atualizado com sucesso: ID={}", voluntarioAtualizado.getIdVoluntario());
            return voluntarioUtil.converterParaVoluntarioOutput(voluntarioAtualizado);
        }
        
        // Buscar usuário
        Usuario usuario = usuarioRepository.findById(voluntarioInput.getFkUsuario())
                .orElseThrow(() -> {
                    LOGGER.warn("Usuário não encontrado: ID={}", voluntarioInput.getFkUsuario());
                    return new IllegalArgumentException("Usuário não encontrado");
                });
        
        // Criar novo voluntário
        Voluntario voluntario = voluntarioUtil.converterParaVoluntario(voluntarioInput, usuario);
        Voluntario voluntarioSalvo = voluntarioRepository.save(voluntario);
        
        LOGGER.info("Voluntário cadastrado com sucesso: ID={}, Usuário ID={}", 
                   voluntarioSalvo.getIdVoluntario(), voluntarioSalvo.getFkUsuario());
        
        // Converter para output e retornar
        return voluntarioUtil.converterParaVoluntarioOutput(voluntarioSalvo);
    }
}