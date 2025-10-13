package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuarioPorIdUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BuscarUsuarioPorIdUseCaseImpl implements BuscarUsuarioPorIdUseCase {

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorIdUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioOutput> executar(Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        return usuarioRepository.findById(id)
                .map(this::toOutput);
    }

    private UsuarioOutput toOutput(Usuario usuario) {
        UsuarioOutput output = new UsuarioOutput();
        BeanUtils.copyProperties(usuario, output);
        return output;
    }
}
