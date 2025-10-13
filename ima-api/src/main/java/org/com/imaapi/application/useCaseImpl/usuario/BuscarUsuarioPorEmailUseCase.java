package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuarioPorEmailUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BuscarUsuarioPorEmailUseCaseImpl implements BuscarUsuarioPorEmailUseCase {

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorEmailUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioOutput> executar(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        return usuarioRepository.findByEmail(email)
                .map(this::toOutput);
    }

    private UsuarioOutput toOutput(Usuario usuario) {
        UsuarioOutput output = new UsuarioOutput();
        BeanUtils.copyProperties(usuario, output);
        return output;
    }
}
