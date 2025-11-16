package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.AtualizarUltimoAcessoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;

@Service
public class AtualizarUltimoAcessoUseCaseImpl implements AtualizarUltimoAcessoUseCase {

    private final UsuarioRepository usuarioRepository;

    public AtualizarUltimoAcessoUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public void executar(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + idUsuario));
        usuario.setUltimoAcesso(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}
