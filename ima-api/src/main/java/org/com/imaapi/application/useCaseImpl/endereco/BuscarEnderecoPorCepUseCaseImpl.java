package org.com.imaapi.application.useCaseImpl.endereco;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.useCase.endereco.BuscarEnderecoPorCepUseCase;
import org.com.imaapi.application.useCase.endereco.CadastrarEnderecoUseCase;
import org.com.imaapi.domain.gateway.ViaCepGateway;
import org.com.imaapi.domain.model.Endereco;
import org.com.imaapi.domain.repository.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuscarEnderecoPorCepUseCaseImpl implements BuscarEnderecoPorCepUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarEnderecoPorCepUseCaseImpl.class);
    
    private final EnderecoRepository enderecoRepository;
    private final ViaCepGateway viaCepGateway;
    private final CadastrarEnderecoUseCase cadastrarEnderecoUseCase;
    private final EnderecoUtil enderecoUtil;
    
    public BuscarEnderecoPorCepUseCaseImpl(
            EnderecoRepository enderecoRepository,
            ViaCepGateway viaCepGateway,
            CadastrarEnderecoUseCase cadastrarEnderecoUseCase,
            EnderecoUtil enderecoUtil) {
        this.enderecoRepository = enderecoRepository;
        this.viaCepGateway = viaCepGateway;
        this.cadastrarEnderecoUseCase = cadastrarEnderecoUseCase;
        this.enderecoUtil = enderecoUtil;
    }
    
    @Override
    public EnderecoOutput buscarEnderecoPorCep(String cep, String numero, String complemento) {
        // Validações de entrada
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("O CEP não pode ser nulo ou vazio.");
        }
        
        cep = enderecoUtil.formatarCep(cep);
        
        if (!enderecoUtil.isValidCep(cep)) {
            throw new IllegalArgumentException("CEP inválido. Deve conter 8 dígitos.");
        }
        
        LOGGER.info("Buscando endereço para CEP: {}, número: {}", cep, numero);
        
        // 1. Primeiro tenta buscar no banco de dados (cache)
        Optional<Endereco> enderecoExistenteOpt = enderecoRepository.findByCepAndNumero(cep, numero);
        
        if (enderecoExistenteOpt.isPresent()) {
            LOGGER.info("Endereço encontrado no banco de dados");
            Endereco enderecoExistente = enderecoExistenteOpt.get();
            
            // Atualiza complemento se necessário
            if (complemento != null && !complemento.trim().isEmpty()
                    && !complemento.equals(enderecoExistente.getComplemento())) {
                enderecoExistente.setComplemento(complemento);
                enderecoExistente = enderecoRepository.save(enderecoExistente);
                LOGGER.info("Complemento atualizado para endereço existente");
            }
            
            return enderecoUtil.converterParaEnderecoOutput(enderecoExistente);
        }
        
        // 2. Se não encontrou no banco, busca na API externa via gateway
        LOGGER.info("Endereço não encontrado no banco, buscando na API ViaCEP");
        
        EnderecoOutput enderecoOutput = viaCepGateway.buscarPorCep(cep);
        
        if (enderecoOutput == null || enderecoOutput.getCep() == null) {
            throw new RuntimeException("Não consegui obter o endereço com esse CEP: " + cep);
        }
        
        // 3. Ajusta dados retornados da API e usa CadastrarEnderecoUseCase
        enderecoOutput.setCep(enderecoUtil.formatarCep(enderecoOutput.getCep()));
        enderecoOutput.setNumero(numero);
        enderecoOutput.setComplemento(complemento);
        
        EnderecoOutput enderecoSalvo = cadastrarEnderecoUseCase.cadastrarEndereco(enderecoOutput, complemento);
        LOGGER.info("Novo endereço salvo no banco de dados via CadastrarEnderecoUseCase");
        
        return enderecoSalvo;
    }
}