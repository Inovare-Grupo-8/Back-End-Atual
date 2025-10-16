package org.com.imaapi.application.useCaseImpl.endereco;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.useCase.endereco.ListarEnderecosUseCase;
import org.com.imaapi.domain.model.Endereco;
import org.com.imaapi.domain.repository.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarEnderecosUseCaseImpl implements ListarEnderecosUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ListarEnderecosUseCaseImpl.class);
    
    private final EnderecoRepository enderecoRepository;
    private final EnderecoUtil enderecoUtil;
    
    public ListarEnderecosUseCaseImpl(EnderecoRepository enderecoRepository, EnderecoUtil enderecoUtil) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoUtil = enderecoUtil;
    }
    
    @Override
    public List<EnderecoOutput> listarEnderecos() {
        LOGGER.info("Listando todos os endereços cadastrados");
        
        List<Endereco> enderecos = enderecoRepository.findAll();
        
        LOGGER.info("Encontrados {} endereços cadastrados", enderecos.size());
        
        return enderecos.stream()
                .map(enderecoUtil::converterParaEnderecoOutput)
                .collect(Collectors.toList());
    }
}