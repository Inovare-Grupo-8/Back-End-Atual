package org.com.imaapi.application.useCase.perfil;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputAtualizacaoDadosPessoais;
import org.com.imaapi.application.dto.usuario.output.UsuarioDadosPessoaisOutput;

public interface AtualizarDadosPessoaisCompletoUseCase {
    UsuarioDadosPessoaisOutput atualizarDadosPessoaisCompleto(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais dadosPessoais);
}