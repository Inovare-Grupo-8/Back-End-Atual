package org.com.imaapi.application.useCase.perfil;

public interface AtualizarEnderecoUseCase {
    boolean atualizarEnderecoPorUsuarioId(Integer usuarioId, String cep, String numero, String complemento);
  
    boolean atualizarEndereco(Integer usuarioId, String cep, String numero, String complemento);
}