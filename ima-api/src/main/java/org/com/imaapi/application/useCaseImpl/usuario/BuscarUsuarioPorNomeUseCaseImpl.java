package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuarioPorNomeUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BuscarUsuarioPorNomeUseCaseImpl implements BuscarUsuarioPorNomeUseCase {

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorNomeUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioOutput> executar(String termo) {
        if (termo == null || termo.isBlank()) {
            return Optional.empty();
        }

        return usuarioRepository.findByNomeContainingIgnoreCase(termo)
                .stream()
                .findFirst()
                .map(this::toOutput);
    }

    private UsuarioOutput toOutput(Usuario usuario) {
        UsuarioOutput output = new UsuarioOutput();
        BeanUtils.copyProperties(usuario, output);
        return output;
    }
}
