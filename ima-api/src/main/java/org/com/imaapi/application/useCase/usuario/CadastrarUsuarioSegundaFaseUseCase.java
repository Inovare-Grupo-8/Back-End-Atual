package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;

public interface CadastrarUsuarioSegundaFaseUseCase {
    UsuarioOutput executar(Integer idUsuario, UsuarioInputSegundaFase usuarioInputSegundaFase);
}