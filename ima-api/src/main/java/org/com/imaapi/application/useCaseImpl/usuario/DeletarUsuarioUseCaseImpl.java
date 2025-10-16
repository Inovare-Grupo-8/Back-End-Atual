package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.DeletarUsuarioUseCase;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletarUsuarioUseCaseImpl implements DeletarUsuarioUseCase {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void executar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
