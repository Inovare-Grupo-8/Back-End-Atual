package org.com.imaapi.application.useCase.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioClassificacaoOutput;

import java.util.List;

public interface BuscarUsuariosNaoClassificadosUseCase {
    List<UsuarioClassificacaoOutput> executar();
}