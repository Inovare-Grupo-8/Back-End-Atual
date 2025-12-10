package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioTokenOutput;
import org.com.imaapi.application.useCase.usuario.AutenticarUsuarioUseCase;
import org.com.imaapi.application.dto.usuario.input.UsuarioAutenticacaoInput;
import org.com.imaapi.domain.gateway.PasswordEncoderGateway;
import org.com.imaapi.domain.gateway.TokenProvider;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AutenticarUsuarioUseCaseImpl implements AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;
    private final TokenProvider tokenProvider;

    public AutenticarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository,
                                        PasswordEncoderGateway passwordEncoderGateway,
                                        TokenProvider tokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoderGateway = passwordEncoderGateway;
        this.tokenProvider = tokenProvider;
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

        if (!passwordEncoderGateway.matches(usuarioAutenticacaoInput.getSenha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        usuario.setUltimoAcesso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        List<String> authorities = List.of("ROLE_" + usuario.getTipo().name());

        String token = tokenProvider.gerarToken(usuario.getEmail(), authorities);

        UsuarioTokenOutput output = new UsuarioTokenOutput();
        output.setEmail(usuario.getEmail());
        output.setTipo(usuario.getTipo());
        output.setIdUsuario(usuario.getIdUsuario());
        output.setToken(token);
        
        // Incluir o nome da ficha se existir
        if (usuario.getFicha() != null) {
            output.setNome(usuario.getFicha().getNome());
        }

        return output;
    }
}
