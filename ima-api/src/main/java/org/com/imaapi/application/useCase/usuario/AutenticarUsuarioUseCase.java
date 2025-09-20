package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioAutenticacaoInput;
import org.com.imaapi.application.dto.usuario.output.UsuarioTokenOutput;

public interface AutenticarUsuarioUseCase {
    UsuarioTokenOutput executar(UsuarioAutenticacaoInput usuarioAutenticacaoInput);
}