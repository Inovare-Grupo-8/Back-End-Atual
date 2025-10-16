package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuarioPorNomeUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
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
    public Optional<UsuarioOutput> executar(String nome) {
        return usuarioRepository.findByFichaNomeContainingIgnoreCase(nome).stream()
                .findFirst()
                .map(usuario -> new UsuarioOutput(
                        usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getFotoUrl(),
                        usuario.getDataCadastro(),
                        usuario.getTipo()
                ));
    }
}
