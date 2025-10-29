package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarUsuarioPrimeiraFaseUseCase;
import org.com.imaapi.application.useCase.email.EnviarEmailUseCase;
import org.com.imaapi.application.useCase.email.GerarConteudoHtmlContinuarCadastroUseCase;
import org.com.imaapi.application.useCase.usuario.EnviarCredenciaisVoluntarioUseCase;
import org.com.imaapi.application.dto.email.EmailDto;
import org.com.imaapi.application.useCaseImpl.email.EmailQueueProducerImpl;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CadastrarUsuarioPrimeiraFaseUseCaseImpl implements CadastrarUsuarioPrimeiraFaseUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CadastrarUsuarioPrimeiraFaseUseCaseImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private EnviarEmailUseCase enviarEmailUseCase;

    @Autowired
    private EmailQueueProducerImpl emailQueueProducerImpl;

    @Autowired
    private GerarConteudoHtmlContinuarCadastroUseCase gerarConteudoHtmlContinuarCadastroUseCase;

    @Autowired
    private EnviarCredenciaisVoluntarioUseCase enviarCredenciaisVoluntarioUseCase;

    @Override
    public UsuarioPrimeiraFaseOutput executar(UsuarioInputPrimeiraFase input) {
        LOGGER.info("Iniciando cadastro de usuário (primeira fase) para email: {}", input.getEmail());

        if (usuarioRepository.findByEmail(input.getEmail()).isPresent()) {
            LOGGER.warn("Usuário com email {} já existe", input.getEmail());
            throw new IllegalArgumentException("Usuário com este email já existe");
        }

        Ficha ficha = new Ficha();
        ficha.setNome(input.getNome());
        ficha.setSobrenome(input.getSobrenome());
        ficha.setCpf(input.getCpf());
        fichaRepository.save(ficha);

        Usuario usuario = new Usuario();
        usuario.setEmail(input.getEmail());
        usuario.setSenha(input.getSenha());
        usuario.setTipo(TipoUsuario.NAO_CLASSIFICADO);
        usuario.setFicha(ficha);
        usuarioRepository.save(usuario);

        LOGGER.info("Usuário cadastrado com sucesso (primeira fase) para email: {}", input.getEmail());

        UsuarioPrimeiraFaseOutput output = new UsuarioPrimeiraFaseOutput();
        output.setIdUsuario(usuario.getIdUsuario());
        output.setNome(ficha.getNome());
        output.setSobrenome(ficha.getSobrenome());
        output.setEmail(usuario.getEmail());
        output.setCpf(ficha.getCpf());

        try {
            LOGGER.info("Iniciando envio de emails para o usuário: {}", input.getEmail());
            
            String nomeCompleto = ficha.getNome() + " " + ficha.getSobrenome();
            
            if (usuario.getTipo() == TipoUsuario.VOLUNTARIO) {
                LOGGER.info("Enviando email com credenciais para voluntário: {}", input.getEmail());
                
                String resultadoCredenciais = enviarCredenciaisVoluntarioUseCase.executar(usuario.getEmail(), nomeCompleto, usuario.getSenha(), usuario.getIdUsuario());
                LOGGER.info("Email com credenciais para voluntário enviado: {}", resultadoCredenciais);
                
            } else {
                LOGGER.info("Enviando email para continuar cadastro do usuário não classificado para fila: {}", input.getEmail());
                
                String dadosContinuarCadastro = nomeCompleto + "|" + usuario.getIdUsuario();
                EmailDto emailContinuarCadastro = new EmailDto(usuario.getEmail(), dadosContinuarCadastro, "continuar cadastro", usuario.getEmail(), usuario.getSenha(), usuario.getIdUsuario());
                emailQueueProducerImpl.enviarEmailParaFila(emailContinuarCadastro);
                LOGGER.info("Email para continuar cadastro enviado para fila com sucesso");
            }
            
        } catch (Exception e) {
            LOGGER.error("Erro ao enviar emails para o usuário: {}. Erro: {}", input.getEmail(), e.getMessage(), e);
        }

        return output;
    }
}