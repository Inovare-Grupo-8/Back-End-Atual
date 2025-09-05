package org.com.imaapi.util.service;

import org.com.imaapi.domain.model.usuario.Endereco;
import org.com.imaapi.domain.model.usuario.usuarioInputDTO.EnderecoInputDTO;
import org.com.imaapi.domain.model.usuario.UsuarioOutputDTO.EnderecoOutputDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EnderecoService {
    ResponseEntity<EnderecoOutputDTO> buscaEndereco(String cep, String numero, String complemento);

    List<EnderecoOutputDTO> listarEnderecos();

    Endereco cadastrarEndereco(EnderecoOutputDTO enderecoOutput, String complemento);

    Endereco criarOuAtualizarEndereco(EnderecoInputDTO enderecoInputDTO);
}
