package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.BuscarEnderecoUseCase;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Endereco;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarEnderecoUseCaseImpl implements BuscarEnderecoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarEnderecoUseCaseImpl.class);

    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public BuscarEnderecoUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public EnderecoOutput buscarEnderecoPorId(Integer usuarioId) {
        LOGGER.info("Buscando endereço para o usuário com ID: {}", usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            LOGGER.warn("Usuário não encontrado para o ID: {}", usuarioId);
            return null;
        }

        Ficha ficha = usuario.getFicha();
        if (ficha == null || ficha.getEndereco() == null) {
            LOGGER.warn("Ficha ou endereço não encontrado para o usuário com ID: {}", usuarioId);
            return null;
        }

        Endereco endereco = ficha.getEndereco();
        EnderecoOutput enderecoOutput = new EnderecoOutput();
        enderecoOutput.setCep(endereco.getCep());
        enderecoOutput.setNumero(endereco.getNumero());
        enderecoOutput.setComplemento(endereco.getComplemento());
        enderecoOutput.setLogradouro(endereco.getLogradouro());
        enderecoOutput.setBairro(endereco.getBairro());
        enderecoOutput.setLocalidade(endereco.getCidade());
        enderecoOutput.setUf(endereco.getUf());

        LOGGER.info("Endereço encontrado - CEP: {}, Número: {}, Cidade: {}",
                endereco.getCep(), endereco.getNumero(), endereco.getCidade());
                
        return enderecoOutput;
    }
    
    @Override
    public EnderecoOutput buscarEndereco(Integer usuarioId) {
        LOGGER.info("Buscando endereço para o usuário com ID: {}", usuarioId);
        return buscarEnderecoPorId(usuarioId);
    }
}