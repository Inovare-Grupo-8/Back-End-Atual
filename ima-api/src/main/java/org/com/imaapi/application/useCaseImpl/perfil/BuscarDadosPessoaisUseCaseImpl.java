package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.BuscarDadosPessoaisUseCase;
import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioDadosPessoaisOutput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarDadosPessoaisUseCaseImpl implements BuscarDadosPessoaisUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuscarDadosPessoaisUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TelefoneRepository telefoneRepository;
    
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Override
    public UsuarioDadosPessoaisOutput buscarDadosPessoais(Integer usuarioId) {
        LOGGER.info("Buscando dados pessoais para o usuário com ID: {}", usuarioId);
        return buscarDadosPessoaisPorId(usuarioId);
    }
    
    @Override
    public UsuarioDadosPessoaisOutput buscarDadosPessoaisPorId(Integer usuarioId) {
        LOGGER.info("Buscando dados pessoais para o usuário com ID: {}", usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            LOGGER.warn("Usuário não encontrado para o ID: {}", usuarioId);
            return null;
        }

        Ficha ficha = usuario.getFicha();
        if (ficha == null) {
            LOGGER.warn("Ficha não encontrada para o usuário com ID: {}", usuarioId);
            return null;
        }

        UsuarioDadosPessoaisOutput dadosPessoais = new UsuarioDadosPessoaisOutput();
        dadosPessoais.setNome(ficha.getNome());
        dadosPessoais.setSobrenome(ficha.getSobrenome());
        dadosPessoais.setCpf(ficha.getCpf());
        dadosPessoais.setDataNascimento(ficha.getDtNascim());
        dadosPessoais.setEmail(usuario.getEmail());
        dadosPessoais.setTipo(usuario.getTipo().toString());

        // Mapear gênero da ficha
        if (ficha.getGenero() != null) {
            dadosPessoais.setGenero(ficha.getGenero());
        }

        // Buscar telefone
        List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        if (!telefones.isEmpty()) {
            Telefone telefone = telefones.get(0);
            if (telefone.getDdd() != null && telefone.getPrefixo() != null && telefone.getSufixo() != null) {
                String telefoneFormatado = String.format("(%s) %s-%s",
                        telefone.getDdd(),
                        telefone.getPrefixo(),
                        telefone.getSufixo());
                dadosPessoais.setTelefone(telefoneFormatado);
                LOGGER.info("Telefone encontrado: {}", telefoneFormatado);
            } else {
                LOGGER.warn("Telefone encontrado, mas incompleto: DDD={}, Prefixo={}, Sufixo={}",
                        telefone.getDdd(), telefone.getPrefixo(), telefone.getSufixo());
                dadosPessoais.setTelefone("");
            }
        } else {
            LOGGER.warn("Nenhum telefone encontrado para a ficha com ID: {}", ficha.getIdFicha());
            dadosPessoais.setTelefone("");
        }

        // Buscar dados profissionais se for administrador (assistente social)
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
        if (voluntario != null) {
            dadosPessoais.setCrp(voluntario.getRegistroProfissional());
            dadosPessoais.setBio(voluntario.getBiografiaProfissional());
            if (voluntario.getFuncao() != null) {
                dadosPessoais.setEspecialidade(voluntario.getFuncao().getValue());
            }
        }

        // Incluir URL da foto de perfil
        if (usuario.getFotoUrl() != null) {
            dadosPessoais.setFotoUrl(usuario.getFotoUrl());
        }

        return dadosPessoais;
    }
    
    @Override
    public AssistenteSocialOutput buscarAssistenteSocial(Integer usuarioId) {
        LOGGER.info("Buscando assistente social para o usuário com ID: {}", usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            LOGGER.warn("Usuário não encontrado para o ID: {}", usuarioId);
            return null;
        }
        
        // Verificar se o usuário é um assistente social
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
        if (voluntario == null || !voluntario.getFuncao().equals("ASSISTENCIA_SOCIAL")) {
            LOGGER.warn("Usuário com ID {} não é um assistente social", usuarioId);
            return null;
        }
        
        AssistenteSocialOutput assistenteSocial = new AssistenteSocialOutput();
        assistenteSocial.setIdUsuario(voluntario.getIdVoluntario());
        assistenteSocial.setNome(usuario.getFicha().getNome());
        assistenteSocial.setSobrenome(usuario.getFicha().getSobrenome());
        
        return assistenteSocial;
    }
}