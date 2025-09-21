package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;

import java.util.Optional;

public interface BuscarUsuarioPorEmailUseCase {
    Optional<UsuarioOutput> executar(String email);
}