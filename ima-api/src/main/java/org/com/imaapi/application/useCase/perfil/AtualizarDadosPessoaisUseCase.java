package org.com.imaapi.application.useCase.perfil;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputAtualizacaoDadosPessoais;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;

public interface AtualizarDadosPessoaisUseCase {
    UsuarioOutput atualizarDadosPessoais(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais dadosPessoais);
}