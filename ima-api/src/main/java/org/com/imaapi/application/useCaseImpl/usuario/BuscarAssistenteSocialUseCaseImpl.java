package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.useCase.usuario.BuscarAssistenteSocialUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarAssistenteSocialUseCaseImpl implements BuscarAssistenteSocialUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarAssistenteSocialUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Override
    public AssistenteSocialOutput executar(Integer usuarioId) {
        LOGGER.info("Buscando assistente social para o usuário com ID: {}", usuarioId);
        
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            LOGGER.warn("Usuário não encontrado para o ID: {}", usuarioId);
            return null;
        }
        
        // Verificar se o usuário é um assistente social
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
        if (voluntario == null || !voluntario.getFuncao().equals("ASSISTENCIA_SOCIAL")) {
            LOGGER.warn("Usuário com ID {} não é um assistente social", usuarioId);
            return null;
        }
        
        AssistenteSocialOutput output = new AssistenteSocialOutput();
        output.setIdUsuario(usuario.getIdUsuario());
        output.setNome(usuario.getFicha().getNome());
        output.setSobrenome(usuario.getFicha().getSobrenome());
        output.setEmail(usuario.getEmail());
        
        // Adicionar mais campos conforme necessário
        
        LOGGER.info("Assistente social encontrado com sucesso. ID: {}", usuarioId);
        return output;
    }
}