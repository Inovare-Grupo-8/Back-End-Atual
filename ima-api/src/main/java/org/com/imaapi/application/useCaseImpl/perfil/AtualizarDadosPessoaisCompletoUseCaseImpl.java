package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.AtualizarDadosPessoaisCompletoUseCase;
import org.com.imaapi.application.dto.usuario.input.UsuarioInputAtualizacaoDadosPessoais;
import org.com.imaapi.application.dto.usuario.output.UsuarioDadosPessoaisOutput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.model.VoluntarioEspecialidade;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.model.enums.Funcao;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.com.imaapi.domain.repository.VoluntarioEspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AtualizarDadosPessoaisCompletoUseCaseImpl implements AtualizarDadosPessoaisCompletoUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarDadosPessoaisCompletoUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TelefoneRepository telefoneRepository;
    
    @Autowired
    private VoluntarioRepository voluntarioRepository;
    
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    
    @Autowired
    private VoluntarioEspecialidadeRepository voluntarioEspecialidadeRepository;

    @Override
    public UsuarioDadosPessoaisOutput atualizarDadosPessoaisCompleto(Integer usuarioId, UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        LOGGER.info("Atualizando dados pessoais completos para o usuário com ID: {}", usuarioId);
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

        // Atualizar dados básicos da ficha
        atualizarDadosBasicos(ficha, dadosPessoais);
        
        // Atualizar email do usuário
        if (dadosPessoais.getEmail() != null && !dadosPessoais.getEmail().trim().isEmpty()) {
            usuario.setEmail(dadosPessoais.getEmail());
            LOGGER.info("Email atualizado para: {}", dadosPessoais.getEmail());
        }

        // Atualizar telefone
        if (dadosPessoais.getTelefone() != null && !dadosPessoais.getTelefone().trim().isEmpty()) {
            atualizarTelefone(ficha, dadosPessoais.getTelefone());
        }

        // Atualizar dados profissionais se for administrador (assistente social)
        if (usuario.getTipo() == TipoUsuario.ADMINISTRADOR) {
            atualizarDadosProfissionais(usuario, dadosPessoais);
        }

        usuarioRepository.save(usuario);
        LOGGER.info("Dados pessoais completos atualizados com sucesso para o usuário com ID: {}", usuarioId);
        
        return criarOutputAtualizado(usuario, ficha);
    }

    private void atualizarDadosBasicos(Ficha ficha, UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        if (dadosPessoais.getNome() != null) {
            ficha.setNome(dadosPessoais.getNome());
        }
        if (dadosPessoais.getSobrenome() != null) {
            ficha.setSobrenome(dadosPessoais.getSobrenome());
        }
        if (dadosPessoais.getDataNascimento() != null) {
            ficha.setDtNascim(dadosPessoais.getDataNascimento());
        }
        
        // Atualizar gênero se fornecido
        if (dadosPessoais.getGenero() != null) {
            ficha.setGenero(dadosPessoais.getGenero());
            LOGGER.info("Gênero atualizado para: {}", dadosPessoais.getGenero());
        }
    }

    private void atualizarTelefone(Ficha ficha, String telefoneString) {
        List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        Telefone telefone;

        if (telefones.isEmpty()) {
            telefone = new Telefone();
            telefone.setFicha(ficha);
            LOGGER.info("Criando novo telefone para ficha ID: {}", ficha.getIdFicha());
        } else {
            telefone = telefones.get(0);
            LOGGER.info("Atualizando telefone existente ID: {}", telefone.getIdTelefone());
        }

        // Parse do telefone formato "(11) 98765-4321" ou "(11) 9876-5432"
        String telefoneCompleto = telefoneString.replaceAll("[()\\s-]", "");

        if (telefoneCompleto.length() >= 10) {
            String ddd = telefoneCompleto.substring(0, 2);
            String numero = telefoneCompleto.substring(2);

            telefone.setDdd(ddd);

            // Dividir número em prefixo e sufixo
            if (numero.length() == 9) { // Celular com 9 dígitos
                telefone.setPrefixo(numero.substring(0, 5));
                telefone.setSufixo(numero.substring(5));
            } else if (numero.length() == 8) { // Fixo com 8 dígitos
                telefone.setPrefixo(numero.substring(0, 4));
                telefone.setSufixo(numero.substring(4));
            } else {
                // Fallback para outros tamanhos
                int splitPoint = numero.length() - 4;
                telefone.setPrefixo(numero.substring(0, splitPoint));
                telefone.setSufixo(numero.substring(splitPoint));
            }

            if (telefone.getWhatsapp() == null) {
                telefone.setWhatsapp(true); // Default para WhatsApp
            }

            telefoneRepository.save(telefone);
            LOGGER.info("Telefone atualizado: DDD={}, Prefixo={}, Sufixo={}", telefone.getDdd(), telefone.getPrefixo(), telefone.getSufixo());
        } else {
            LOGGER.warn("Telefone inválido fornecido: {}", telefoneString);
        }
    }

    private void atualizarDadosProfissionais(Usuario usuario, UsuarioInputAtualizacaoDadosPessoais dadosPessoais) {
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
        if (voluntario != null) {
            boolean voluntarioAtualizado = false;

            if (dadosPessoais.getCrp() != null) {
                voluntario.setRegistroProfissional(dadosPessoais.getCrp());
                voluntarioAtualizado = true;
                LOGGER.info("CRP atualizado para: {}", dadosPessoais.getCrp());
            }

            if (dadosPessoais.getBio() != null) {
                voluntario.setBiografiaProfissional(dadosPessoais.getBio());
                voluntarioAtualizado = true;
                LOGGER.info("Bio profissional atualizada");
            }
            
            if (dadosPessoais.getEspecialidade() != null) {
                try {
                    Funcao funcao = Funcao.fromValue(dadosPessoais.getEspecialidade());
                    voluntario.setFuncao(funcao);
                    voluntarioAtualizado = true;
                    LOGGER.info("Especialidade atualizada para: {}", dadosPessoais.getEspecialidade());
                } catch (Exception e) {
                    LOGGER.warn("Especialidade inválida fornecida: {}", dadosPessoais.getEspecialidade());
                }
            }

            if (voluntarioAtualizado) {
                voluntarioRepository.save(voluntario);
            }
        }
    }

    private UsuarioDadosPessoaisOutput criarOutputAtualizado(Usuario usuario, Ficha ficha) {
        UsuarioDadosPessoaisOutput output = new UsuarioDadosPessoaisOutput();
        output.setNome(ficha.getNome());
        output.setSobrenome(ficha.getSobrenome());
        output.setCpf(ficha.getCpf());
        output.setDataNascimento(ficha.getDtNascim());
        output.setEmail(usuario.getEmail());
        output.setTipo(usuario.getTipo().toString());
        
        // Incluir gênero no output
        if (ficha.getGenero() != null) {
            output.setGenero(ficha.getGenero());
        }

        // Buscar telefone atualizado para retorno
        List<Telefone> telefonesAtualizados = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        if (!telefonesAtualizados.isEmpty()) {
            Telefone telefoneAtualizado = telefonesAtualizados.get(0);
            String telefoneFormatado = String.format("(%s) %s-%s",
                    telefoneAtualizado.getDdd(),
                    telefoneAtualizado.getPrefixo(),
                    telefoneAtualizado.getSufixo());
            output.setTelefone(telefoneFormatado);
        } else {
            output.setTelefone("");
        }

        // Incluir dados profissionais atualizados se for administrador
        if (usuario.getTipo() == TipoUsuario.ADMINISTRADOR) {
            Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
            if (voluntario != null) {
                output.setCrp(voluntario.getRegistroProfissional());
                output.setBio(voluntario.getBiografiaProfissional());
                if (voluntario.getFuncao() != null) {
                    output.setEspecialidade(voluntario.getFuncao().getValue());
                }
            }
        }
        
        return output;
    }
}