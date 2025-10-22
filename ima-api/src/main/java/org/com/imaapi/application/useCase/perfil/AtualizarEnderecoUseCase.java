package org.com.imaapi.application.useCase.perfil;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;

public interface AtualizarEnderecoUseCase {
    boolean atualizarEnderecoPorUsuarioId(Integer usuarioId, String cep, String numero, String complemento);
  
    EnderecoOutput atualizarEndereco(Integer usuarioId, String cep, String numero, String complemento);
}