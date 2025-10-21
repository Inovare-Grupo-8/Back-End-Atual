package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.AtualizarDadosPessoaisUseCase;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputAtualizacaoDadosPessoais;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.dto.usuario.output.TelefoneOutput;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.useCaseImpl.endereco.EnderecoUtil;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AtualizarDadosPessoaisUseCaseImpl implements AtualizarDadosPessoaisUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarDadosPessoaisUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private EnderecoUtil enderecoUtil;

    @Override
    public UsuarioOutput atualizarDadosPessoais(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        LOGGER.info("Iniciando atualização de dados pessoais para o usuário com ID: {}", usuarioId);

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            LOGGER.error("Usuário não encontrado com ID: {}", usuarioId);
            return null;
        }

        Ficha ficha = usuario.getFicha();
        if (ficha == null) {
            LOGGER.error("Ficha não encontrada para o usuário com ID: {}", usuarioId);
            return null;
        }

        // Atualizar dados da ficha
        if (dadosPessoais.getNome() != null && !dadosPessoais.getNome().isEmpty()) {
            ficha.setNome(dadosPessoais.getNome());
        }

        if (dadosPessoais.getSobrenome() != null && !dadosPessoais.getSobrenome().isEmpty()) {
            ficha.setSobrenome(dadosPessoais.getSobrenome());
        }

        if (dadosPessoais.getDataNascimento() != null) {
            ficha.setDtNascim(dadosPessoais.getDataNascimento());
        }

        if (dadosPessoais.getGenero() != null) {
            ficha.setGenero(dadosPessoais.getGenero());
        }

        // Atualizar email do usuário
        if (dadosPessoais.getEmail() != null && !dadosPessoais.getEmail().isEmpty()) {
            usuario.setEmail(dadosPessoais.getEmail());
        }

        // Atualizar telefone se fornecido
        if (dadosPessoais.getTelefone() != null && !dadosPessoais.getTelefone().isEmpty()) {
            atualizarTelefone(ficha, dadosPessoais.getTelefone());
        }

        usuarioRepository.save(usuario);
        LOGGER.info("Dados pessoais atualizados com sucesso para o usuário com ID: {}", usuarioId);

        return construirUsuarioOutput(usuario, ficha);
    }

    private UsuarioOutput construirUsuarioOutput(Usuario usuario, Ficha ficha) {
        UsuarioOutput output = new UsuarioOutput();
        
        // Dados básicos do usuário (primeira fase)
        output.setId(usuario.getIdUsuario());
        output.setNome(ficha.getNome());
        output.setSobrenome(ficha.getSobrenome());
        output.setCpf(ficha.getCpf());
        output.setEmail(usuario.getEmail());
        output.setDataCadastro(usuario.getCriadoEm());
        
        // Dados da segunda fase
        output.setDataNascimento(ficha.getDtNascim());
        
        if (ficha.getRendaMinima() != null) {
            output.setRendaMinima(ficha.getRendaMinima().doubleValue());
        }
        
        if (ficha.getRendaMaxima() != null) {
            output.setRendaMaxima(ficha.getRendaMaxima().doubleValue());
        }
        
        if (ficha.getGenero() != null) {
            output.setGenero(ficha.getGenero().toString());
        }
        
        output.setTipo(usuario.getTipo());
        output.setAreaOrientacao(ficha.getAreaOrientacao());
        output.setComoSoube(ficha.getComoSoube());
        output.setProfissao(ficha.getProfissao());
        
        // Buscar dados do voluntário se aplicável
        if (usuario.getTipo() != null && usuario.getTipo().toString().equals("VOLUNTARIO")) {
            Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
            if (voluntario != null) {
                output.setFuncao(voluntario.getFuncao());
            }
        }
        
        // Buscar telefone
        List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        if (!telefones.isEmpty()) {
            output.setTelefone(TelefoneOutput.fromEntity(telefones.get(0)));
        }
        
        // Buscar endereço
        if (ficha.getEndereco() != null) {
            output.setEndereco(enderecoUtil.converterParaEnderecoOutput(ficha.getEndereco()));
        }
        
        return output;
    }

    private void atualizarTelefone(Ficha ficha, String telefoneString) {
        // Buscar telefone existente
        List<Telefone> telefonesExistentes = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        
        if (!telefonesExistentes.isEmpty()) {
            // Atualizar telefone existente
            Telefone telefoneExistente = telefonesExistentes.get(0);
            
            // Parse do telefone (assumindo formato: (XX) XXXXX-XXXX)
            String telefoneNumerico = telefoneString.replaceAll("[^0-9]", "");
            
            if (telefoneNumerico.length() >= 10) {
                String ddd = telefoneNumerico.substring(0, 2);
                String numero = telefoneNumerico.substring(2);
                
                telefoneExistente.setDdd(ddd);
                
                if (numero.length() == 9) {
                    // Celular: XXXXX-XXXX
                    telefoneExistente.setPrefixo(numero.substring(0, 5));
                    telefoneExistente.setSufixo(numero.substring(5));
                } else if (numero.length() == 8) {
                    // Fixo: XXXX-XXXX
                    telefoneExistente.setPrefixo(numero.substring(0, 4));
                    telefoneExistente.setSufixo(numero.substring(4));
                }
                
                telefoneRepository.save(telefoneExistente);
                LOGGER.info("Telefone atualizado para a ficha ID: {}", ficha.getIdFicha());
            }
        } else {
            // Criar novo telefone se não existir
            String telefoneNumerico = telefoneString.replaceAll("[^0-9]", "");
            
            if (telefoneNumerico.length() >= 10) {
                Telefone novoTelefone = new Telefone();
                novoTelefone.setFicha(ficha);
                
                String ddd = telefoneNumerico.substring(0, 2);
                String numero = telefoneNumerico.substring(2);
                
                novoTelefone.setDdd(ddd);
                
                if (numero.length() == 9) {
                    // Celular: XXXXX-XXXX
                    novoTelefone.setPrefixo(numero.substring(0, 5));
                    novoTelefone.setSufixo(numero.substring(5));
                } else if (numero.length() == 8) {
                    // Fixo: XXXX-XXXX
                    novoTelefone.setPrefixo(numero.substring(0, 4));
                    novoTelefone.setSufixo(numero.substring(4));
                }
                
                telefoneRepository.save(novoTelefone);
                LOGGER.info("Novo telefone criado para a ficha ID: {}", ficha.getIdFicha());
            }
        }
    }
}