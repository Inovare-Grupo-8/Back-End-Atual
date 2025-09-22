package org.com.imaapi.application.useCase.endereco;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;

public interface CadastrarEnderecoUseCase {
    EnderecoOutput cadastrarEndereco(EnderecoOutput enderecoOutput, String complemento);
}