package org.com.imaapi.application.useCaseImpl;

public interface EnderecoServiceImpl {
    ResponseEntity<EnderecoOutput> buscaEndereco(String cep, String numero, String complemento);

    List<EnderecoOutput> listarEnderecos();

    Endereco cadastrarEndereco(EnderecoOutput enderecoOutput, String complemento);

    Endereco criarOuAtualizarEndereco(EnderecoInput enderecoInput);
}
