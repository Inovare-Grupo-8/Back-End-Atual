package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.DeletarUsuarioUseCase;
import org.com.imaapi.domain.repository.UsuarioRepository;

public class DeletarUsuarioUseCaseImpl implements DeletarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void executar(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        usuarioRepository.deleteById(id);
    }
}
