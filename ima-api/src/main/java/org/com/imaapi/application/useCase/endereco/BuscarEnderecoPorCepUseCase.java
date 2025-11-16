package org.com.imaapi.application.useCase.endereco;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;

public interface BuscarEnderecoPorCepUseCase {
    EnderecoOutput buscarEnderecoPorCep(String cep, String numero, String complemento);
}