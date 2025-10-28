package org.com.imaapi.infrastructure.config.autenticacao;

import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.infrastructure.model.usuario.UsuarioDetalhes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FichaRepository fichaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        LOGGER.info("[AUTENTICAR_SERVICE] Buscando usuário para autenticação: {}", username);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {
            LOGGER.error("[AUTENTICAR_SERVICE] Usuário não encontrado: {}", username);
            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
        }

        Usuario usuario = usuarioOpt.get();
//        LOGGER.info("[AUTENTICAR_SERVICE] Usuário encontrado: ID={}, email={}",
//                usuario.getIdUsuario(), usuario.getEmail());
//        LOGGER.debug("[AUTENTICAR_SERVICE] Senha hash do usuário: {}", usuario.getSenha());

        try {
            Integer fichaId = usuario.getFicha() != null ? usuario.getFicha().getIdFicha() : null;
//            LOGGER.debug("[AUTENTICAR_SERVICE] Buscando ficha com ID: {}", fichaId);

            if (fichaId == null) {
                LOGGER.error("[AUTENTICAR_SERVICE] ID da ficha é nulo para o usuário: {}", username);
                throw new UsernameNotFoundException("Ficha não encontrada para o usuário: ID nulo");
            }

            Ficha ficha = fichaRepository.findById(fichaId)
                    .orElseThrow(() -> new UsernameNotFoundException("Ficha não encontrada para o usuário."));

//            LOGGER.info("[AUTENTICAR_SERVICE] Ficha encontrada para o usuário: {}", username);
            return new UsuarioDetalhes(usuario, ficha);
//            LOGGER.info("[AUTENTICAR_SERVICE] UserDetails criado com sucesso para: {}, autoridades: {}",
//                    username, userDetails.getAuthorities());
        } catch (Exception e) {
            LOGGER.error("[AUTENTICAR_SERVICE] Erro ao carregar ficha do usuário {}: {}",
                    username, e.getMessage(), e);
            throw e;
        }
    }

}
