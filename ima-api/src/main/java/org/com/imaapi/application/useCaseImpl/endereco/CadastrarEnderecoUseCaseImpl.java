package org.com.imaapi.application.useCaseImpl.endereco;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.useCase.endereco.CadastrarEnderecoUseCase;
import org.com.imaapi.domain.model.Endereco;
import org.com.imaapi.domain.repository.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CadastrarEnderecoUseCaseImpl implements CadastrarEnderecoUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CadastrarEnderecoUseCaseImpl.class);
    
    private final EnderecoRepository enderecoRepository;
    private final EnderecoUtil enderecoUtil;
    
    public CadastrarEnderecoUseCaseImpl(EnderecoRepository enderecoRepository, EnderecoUtil enderecoUtil) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoUtil = enderecoUtil;
    }
    
    @Override
    public EnderecoOutput cadastrarEndereco(EnderecoOutput enderecoOutput, String complemento) {
        // Validações de entrada usando EnderecoUtil
        enderecoUtil.validarEnderecoOutput(enderecoOutput);
        
        String cep = enderecoUtil.formatarCep(enderecoOutput.getCep());
        
        LOGGER.info("Cadastrando novo endereço com CEP: {}", cep);
        
        // Criar nova entidade Endereco
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setLogradouro(enderecoOutput.getLogradouro());
        endereco.setBairro(enderecoOutput.getBairro());
        endereco.setNumero(enderecoOutput.getNumero());
        endereco.setUf(enderecoOutput.getUf());
        endereco.setCidade(enderecoOutput.getLocalidade());
        endereco.setComplemento(complemento);
        
        // Salvar no banco
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        
        LOGGER.info("Endereço cadastrado com sucesso: ID={}", enderecoSalvo.getIdEndereco());
        
        // Converter para DTO e retornar
        return enderecoUtil.converterParaEnderecoOutput(enderecoSalvo);
    }
}