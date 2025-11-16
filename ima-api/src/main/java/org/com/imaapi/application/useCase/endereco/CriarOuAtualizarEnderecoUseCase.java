package org.com.imaapi.application.useCase.endereco;


import org.com.imaapi.application.dto.usuario.input.EnderecoInput;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;

public interface CriarOuAtualizarEnderecoUseCase {
    EnderecoOutput criarOuAtualizarEndereco(EnderecoInput enderecoInput);
}