package org.com.imaapi.domain.gateway;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;


// Define o contrato para buscar dados de endereço por CEP.
public interface ViaCepGateway {
    EnderecoOutput buscarPorCep(String cep);
}