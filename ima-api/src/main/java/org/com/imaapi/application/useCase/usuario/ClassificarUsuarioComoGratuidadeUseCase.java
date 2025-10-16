package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;

public interface ClassificarUsuarioComoGratuidadeUseCase {
    UsuarioListarOutput executar(Integer id);
}