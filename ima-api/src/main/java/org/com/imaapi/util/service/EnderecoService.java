package org.com.imaapi.util.service;

import org.com.imaapi.domain.model.usuario.Endereco;
import org.com.imaapi.domain.model.usuario.input.EnderecoInput;
import org.com.imaapi.domain.model.usuario.output.EnderecoOutput;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EnderecoService {
    ResponseEntity<EnderecoOutput> buscaEndereco(String cep, String numero, String complemento);

    List<EnderecoOutput> listarEnderecos();

    Endereco cadastrarEndereco(EnderecoOutput enderecoOutput, String complemento);

    Endereco criarOuAtualizarEndereco(EnderecoInput enderecoInput);
}
