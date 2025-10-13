package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.AtualizarEnderecoUseCase;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Endereco;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.EnderecoRepository;
import org.com.imaapi.infrastructure.gateway.ViaCepClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
public class AtualizarEnderecoUseCaseImpl implements AtualizarEnderecoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarEnderecoUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private ViaCepClient viaCepClient;

    @Override
    public boolean atualizarEnderecoPorUsuarioId(Integer usuarioId, String cep, String numero, String complemento) {
        LOGGER.info("Iniciando atualização de endereço para o usuário com ID: {}", usuarioId);
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            if (usuario == null) {
                LOGGER.warn("Usuário não encontrado para o ID: {}", usuarioId);
                return false;
            }

            Ficha ficha = usuario.getFicha();
            if (ficha == null) {
                LOGGER.warn("Ficha não encontrada para o usuário com ID: {}", usuarioId);
                return false;
            }

            ResponseEntity<EnderecoOutput> enderecoApiResponse = viaCepClient.buscaEndereco(cep, numero, complemento);
            if (enderecoApiResponse == null || enderecoApiResponse.getBody() == null) {
                LOGGER.warn("Endereço não encontrado na API para o CEP: {}", cep);
                return false;
            }
            
            EnderecoOutput enderecoApi = enderecoApiResponse.getBody();

            Endereco endereco = ficha.getEndereco();
            if (endereco == null) {
                endereco = new Endereco();
                ficha.setEndereco(endereco);
            }

            endereco.setCep(enderecoApi.getCep());
            endereco.setLogradouro(enderecoApi.getLogradouro());
            endereco.setBairro(enderecoApi.getBairro());
            endereco.setCidade(enderecoApi.getLocalidade());
            endereco.setUf(enderecoApi.getUf());
            endereco.setNumero(numero);
            endereco.setComplemento(complemento);

            enderecoRepository.save(endereco);
            usuarioRepository.save(usuario);

            LOGGER.info("Endereço atualizado com sucesso para o usuário com ID: {}", usuarioId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar endereço para o usuário com ID: {}", usuarioId, e);
            return false;
        }
    }
    
    @Override
    public boolean atualizarEndereco(Integer usuarioId, String cep, String numero, String complemento) {
        return atualizarEnderecoPorUsuarioId(usuarioId, cep, numero, complemento);
    }
}