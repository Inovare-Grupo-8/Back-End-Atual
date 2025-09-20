package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;

public interface AtualizarUsuarioUseCase {
    UsuarioListarOutput executar(Integer id, UsuarioInputSegundaFase usuarioInputSegundaFase);
}