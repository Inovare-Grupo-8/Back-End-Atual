package org.com.imaapi.application.useCaseImpl.endereco;

import org.com.imaapi.application.dto.usuario.input.EnderecoInput;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.useCase.endereco.BuscarEnderecoPorCepUseCase;
import org.com.imaapi.application.useCase.endereco.CadastrarEnderecoUseCase;
import org.com.imaapi.application.useCase.endereco.CriarOuAtualizarEnderecoUseCase;
import org.com.imaapi.domain.model.Endereco;
import org.com.imaapi.domain.repository.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CriarOuAtualizarEnderecoUseCaseImpl implements CriarOuAtualizarEnderecoUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CriarOuAtualizarEnderecoUseCaseImpl.class);
    
    private final EnderecoRepository enderecoRepository;
    private final BuscarEnderecoPorCepUseCase buscarEnderecoPorCepUseCase;
    private final CadastrarEnderecoUseCase cadastrarEnderecoUseCase;
    private final EnderecoUtil enderecoUtil;
    
    public CriarOuAtualizarEnderecoUseCaseImpl(
            EnderecoRepository enderecoRepository,
            BuscarEnderecoPorCepUseCase buscarEnderecoPorCepUseCase,
            CadastrarEnderecoUseCase cadastrarEnderecoUseCase,
            EnderecoUtil enderecoUtil) {
        this.enderecoRepository = enderecoRepository;
        this.buscarEnderecoPorCepUseCase = buscarEnderecoPorCepUseCase;
        this.cadastrarEnderecoUseCase = cadastrarEnderecoUseCase;
        this.enderecoUtil = enderecoUtil;
    }
    
    @Override
    public EnderecoOutput criarOuAtualizarEndereco(EnderecoInput enderecoInput) {
        // Validação de entrada
        if (enderecoInput == null) {
            throw new IllegalArgumentException("O objeto EnderecoInput não pode ser nulo.");
        }
        
        LOGGER.info("Iniciando criação/atualização de endereço com CEP: {}", enderecoInput.getCep());
        
        String cep = enderecoUtil.formatarCep(enderecoInput.getCep());
        String numero = enderecoInput.getNumero();
        String complemento = enderecoInput.getComplemento();
        
        if (!enderecoUtil.isValidCep(cep)) {
            throw new IllegalArgumentException("CEP inválido. Deve conter 8 dígitos.");
        }
        
        // Primeiro verifica se já existe um endereço com esse CEP e número
        Optional<Endereco> enderecoExistente = enderecoRepository.findByCepAndNumero(cep, numero);
        
        if (enderecoExistente.isPresent()) {
            LOGGER.info("Endereço existente encontrado, atualizando se necessário");
            Endereco endereco = enderecoExistente.get();
            
            // Atualiza complemento se necessário
            if (complemento != null && !complemento.equals(endereco.getComplemento())) {
                endereco.setComplemento(complemento);
                endereco = enderecoRepository.save(endereco);
                LOGGER.info("Endereço atualizado com sucesso: {}", endereco.getIdEndereco());
            }
            
            return enderecoUtil.converterParaEnderecoOutput(endereco);
        }
        
        // Se não existe, busca o endereço na API do ViaCEP e cria um novo
        LOGGER.info("Endereço não encontrado, buscando dados na API ViaCEP");
        
        EnderecoOutput enderecoDetalhes = null;
        try {
            enderecoDetalhes = buscarEnderecoPorCepUseCase.buscarEnderecoPorCep(cep, numero, complemento);
        } catch (Exception e) {
            LOGGER.error("Erro ao buscar detalhes do CEP: {}", e.getMessage());
        }
        
        if (enderecoDetalhes == null) {
            LOGGER.error("Não foi possível obter os dados do endereço para o CEP: {}. Verifique se o CEP está correto.", cep);
            throw new RuntimeException("CEP não encontrado ou inválido. Verifique se o CEP " + cep + " está correto e tente novamente.");
        }
        
        LOGGER.info("Dados obtidos com sucesso, retornando endereço");
        return enderecoDetalhes;
    }
}