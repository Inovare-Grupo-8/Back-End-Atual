package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;

import java.util.Optional;

public interface BuscarUsuarioPorIdUseCase {
    Optional<UsuarioOutput> executar(Integer id);
}