package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;

import java.util.List;

public interface BuscarTodosUsuariosUseCase {
    List<UsuarioListarOutput> executar();
}