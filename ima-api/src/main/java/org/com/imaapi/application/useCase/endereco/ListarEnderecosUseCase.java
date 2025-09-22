package org.com.imaapi.application.useCase.endereco;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;

import java.util.List;

public interface ListarEnderecosUseCase {
    List<EnderecoOutput> listarEnderecos();
}