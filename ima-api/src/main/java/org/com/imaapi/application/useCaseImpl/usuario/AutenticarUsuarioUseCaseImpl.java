package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.AutenticarUsuarioUseCase;
import org.com.imaapi.application.dto.usuario.input.UsuarioAutenticacaoInput;
import org.com.imaapi.application.dto.usuario.output.UsuarioTokenOutput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AutenticarUsuarioUseCaseImpl implements AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public AutenticarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioTokenOutput executar(UsuarioAutenticacaoInput usuarioAutenticacaoInput) {
        if (usuarioAutenticacaoInput == null
                || usuarioAutenticacaoInput.getEmail() == null
                || usuarioAutenticacaoInput.getSenha() == null) {
            throw new IllegalArgumentException("Dados de autenticação incompletos");
        }

        Usuario usuario = usuarioRepository.findByEmail(usuarioAutenticacaoInput.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o email informado"));

        if (!usuario.getSenha().equals(usuarioAutenticacaoInput.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        usuario.setUltimoAcesso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        UsuarioTokenOutput output = new UsuarioTokenOutput();
        BeanUtils.copyProperties(usuario, output);
        output.setToken(UUID.randomUUID().toString());

        return output;
    }
}
