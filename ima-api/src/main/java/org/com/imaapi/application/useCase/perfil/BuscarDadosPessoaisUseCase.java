package org.com.imaapi.application.useCase.perfil;

import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioDadosPessoaisOutput;

public interface BuscarDadosPessoaisUseCase {
    UsuarioDadosPessoaisOutput buscarDadosPessoaisPorId(Integer usuarioId);

    UsuarioDadosPessoaisOutput buscarDadosPessoais(Integer usuarioId);
 
    AssistenteSocialOutput buscarAssistenteSocial(Integer usuarioId);
}