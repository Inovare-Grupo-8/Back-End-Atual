package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.dto.usuario.output.TelefoneOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarVoluntarioSegundaFaseUseCase;
import org.com.imaapi.application.useCase.endereco.CriarOuAtualizarEnderecoUseCase;
import org.com.imaapi.application.dto.usuario.input.EnderecoInput;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.com.imaapi.domain.repository.EnderecoRepository;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.application.useCaseImpl.endereco.EnderecoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CadastrarVoluntarioSegundaFaseUseCaseImpl implements CadastrarVoluntarioSegundaFaseUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CadastrarVoluntarioSegundaFaseUseCaseImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final CriarOuAtualizarEnderecoUseCase criarOuAtualizarEnderecoUseCase;
    private final TelefoneRepository telefoneRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final org.com.imaapi.domain.repository.EnderecoRepository enderecoRepository;
    private final org.com.imaapi.application.useCaseImpl.endereco.EnderecoUtil enderecoUtil;
    private final org.com.imaapi.domain.repository.FichaRepository fichaRepository;

    public CadastrarVoluntarioSegundaFaseUseCaseImpl(
            UsuarioRepository usuarioRepository,
            CriarOuAtualizarEnderecoUseCase criarOuAtualizarEnderecoUseCase,
            TelefoneRepository telefoneRepository,
            VoluntarioRepository voluntarioRepository,
            EnderecoRepository enderecoRepository,
            EnderecoUtil enderecoUtil,
            FichaRepository fichaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.criarOuAtualizarEnderecoUseCase = criarOuAtualizarEnderecoUseCase;
        this.telefoneRepository = telefoneRepository;
        this.voluntarioRepository = voluntarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoUtil = enderecoUtil;
        this.fichaRepository = fichaRepository;
    }

    @Override
    @Transactional
    public UsuarioOutput executar(Integer idUsuario, UsuarioInputSegundaFase usuarioInputSegundaFase) {
        LOGGER.info("Iniciando cadastro da segunda fase para voluntário ID: {}", idUsuario);
        
        if (idUsuario == null || usuarioInputSegundaFase == null) {
            LOGGER.error("Parâmetros inválidos: idUsuario={}, usuarioInputSegundaFase={}", idUsuario, usuarioInputSegundaFase);
            return null;
        }

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            LOGGER.error("Usuário não encontrado com ID: {}", idUsuario);
            return null;
        }

        Ficha ficha = usuario.getFicha();
        if (ficha == null) {
            LOGGER.error("Ficha não encontrada para usuário ID: {}", idUsuario);
            return null;
        }

        // Atualizar dados da ficha
        atualizarDadosFicha(ficha, usuarioInputSegundaFase);
        
        // Atualizar tipo do usuário se fornecido
        if (usuarioInputSegundaFase.getTipo() != null) {
            usuario.setTipo(usuarioInputSegundaFase.getTipo());
        }

        // Salvar usuário atualizado
        usuario = usuarioRepository.save(usuario);

        // Atualizar dados do voluntário se aplicável
        Voluntario voluntario = null;
        if (usuarioInputSegundaFase.getFuncao() != null) {
            voluntario = atualizarVoluntario(usuario, usuarioInputSegundaFase.getFuncao().name());
        }

        // Processar endereço
        EnderecoOutput enderecoOutput = null;
        if (usuarioInputSegundaFase.getEndereco() != null) {
            enderecoOutput = criarOuAtualizarEnderecoUseCase.criarOuAtualizarEndereco(usuarioInputSegundaFase.getEndereco());
            // Associar endereço à ficha se necessário (persistir fk_endereco)
            if (enderecoOutput != null && ficha.getEndereco() == null) {
                try {
                    String cepFormatado = enderecoUtil.formatarCep(usuarioInputSegundaFase.getEndereco().getCep());
                    String numero = usuarioInputSegundaFase.getEndereco().getNumero();
                    enderecoRepository.findByCepAndNumero(cepFormatado, numero).ifPresent(enderecoEntity -> {
                        ficha.setEndereco(enderecoEntity);
                        fichaRepository.save(ficha);
                        LOGGER.info("Endereço associado à ficha do voluntário idUsuario={}", idUsuario);
                    });
                } catch (Exception e) {
                    LOGGER.warn("Não foi possível associar o endereço à ficha do voluntário id={}: {}", idUsuario, e.getMessage());
                }
            }
        }

        // Processar telefone
        TelefoneOutput telefoneOutput = null;
        if (usuarioInputSegundaFase.getTelefone() != null) {
            telefoneOutput = processarTelefone(ficha, usuarioInputSegundaFase.getTelefone());
        }

        LOGGER.info("Cadastro da segunda fase concluído para voluntário ID: {}", idUsuario);
        
        // Construir e retornar o output completo
        return construirUsuarioOutput(usuario, ficha, voluntario, enderecoOutput, telefoneOutput);
    }

    private void atualizarDadosFicha(Ficha ficha, UsuarioInputSegundaFase input) {
        if (input.getDataNascimento() != null) {
            ficha.setDtNascim(input.getDataNascimento());
        }
        
        if (input.getRendaMinima() != null) {
            ficha.setRendaMinima(BigDecimal.valueOf(input.getRendaMinima()));
        }
        
        if (input.getRendaMaxima() != null) {
            ficha.setRendaMaxima(BigDecimal.valueOf(input.getRendaMaxima()));
        }
        
        if (input.getGenero() != null) {
            try {
                ficha.setGenero(org.com.imaapi.domain.model.enums.Genero.valueOf(input.getGenero()));
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Gênero inválido fornecido: {}", input.getGenero());
            }
        }
        
        if (input.getAreaOrientacao() != null) {
            ficha.setAreaOrientacao(input.getAreaOrientacao());
        }
        
        if (input.getComoSoube() != null) {
            ficha.setComoSoube(input.getComoSoube());
        }
        
        if (input.getProfissao() != null) {
            ficha.setProfissao(input.getProfissao());
        }
    }

    private Voluntario atualizarVoluntario(Usuario usuario, String funcao) {
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());

        if (voluntario == null) {
            // criar voluntário se não existir
            voluntario = new Voluntario();
            voluntario.setFkUsuario(usuario.getIdUsuario());
            voluntario.setUsuario(usuario);
            LOGGER.debug("Criando novo voluntário para usuario id={}", usuario.getIdUsuario());
        }

        try {
            voluntario.setFuncao(org.com.imaapi.domain.model.enums.Funcao.valueOf(funcao));
            voluntario = voluntarioRepository.save(voluntario);
            LOGGER.info("Função do voluntário salva: {} para usuario id={}", funcao, usuario.getIdUsuario());
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Função inválida fornecida: {}", funcao);
        }

        return voluntario;
    }

    private TelefoneOutput processarTelefone(Ficha ficha, org.com.imaapi.application.dto.usuario.input.TelefoneInput telefoneInput) {
        // Buscar telefone existente ou criar novo
        List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        Telefone telefone;
        
        if (telefones.isEmpty()) {
            telefone = new Telefone();
            telefone.setFicha(ficha);
        } else {
            telefone = telefones.get(0); // Usar o primeiro telefone encontrado
        }
        
        // Atualizar dados do telefone
        telefone.setDdd(telefoneInput.getDdd());
        telefone.setPrefixo(telefoneInput.getPrefixo());
        telefone.setSufixo(telefoneInput.getSufixo());
        telefone.setWhatsapp(telefoneInput.getWhatsapp());
        
        // Salvar telefone
        telefone = telefoneRepository.save(telefone);
        
        // Converter para output
        return TelefoneOutput.fromEntity(telefone);
    }

    private UsuarioOutput construirUsuarioOutput(Usuario usuario, Ficha ficha, Voluntario voluntario, EnderecoOutput enderecoOutput, TelefoneOutput telefoneOutput) {
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
        output.setEndereco(enderecoOutput);
        output.setTelefone(telefoneOutput);
        output.setAreaOrientacao(ficha.getAreaOrientacao());
        output.setComoSoube(ficha.getComoSoube());
        output.setProfissao(ficha.getProfissao());
        
        // Dados específicos do voluntário
        if (voluntario != null) {
            output.setFuncao(voluntario.getFuncao());
        }
        
        return output;
    }
}
