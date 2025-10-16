package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.com.imaapi.application.useCase.usuario.BuscarTodosUsuariosUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuscarTodosUsuariosUseCaseImpl implements BuscarTodosUsuariosUseCase {

    private final UsuarioRepository usuarioRepository;

    public BuscarTodosUsuariosUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioListarOutput> executar() {
        return usuarioRepository.findAll().stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    private UsuarioListarOutput toOutput(Usuario usuario) {
        UsuarioListarOutput output = new UsuarioListarOutput();
        BeanUtils.copyProperties(usuario, output);
        return output;
    }
}
