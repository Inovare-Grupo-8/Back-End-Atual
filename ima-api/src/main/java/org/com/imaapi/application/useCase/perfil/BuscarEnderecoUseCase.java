package org.com.imaapi.application.useCase.perfil;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;

public interface BuscarEnderecoUseCase {
    EnderecoOutput buscarEnderecoPorId(Integer usuarioId);
}