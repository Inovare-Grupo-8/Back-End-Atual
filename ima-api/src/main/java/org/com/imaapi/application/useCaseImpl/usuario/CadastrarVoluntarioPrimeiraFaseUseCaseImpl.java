package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarVoluntarioPrimeiraFaseUseCase;
import org.com.imaapi.application.useCase.usuario.EnviarCredenciaisVoluntarioUseCase;
import org.com.imaapi.domain.gateway.PasswordEncoderGateway;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Transactional
public class CadastrarVoluntarioPrimeiraFaseUseCaseImpl implements CadastrarVoluntarioPrimeiraFaseUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CadastrarVoluntarioPrimeiraFaseUseCaseImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final FichaRepository fichaRepository;
    private final EnviarCredenciaisVoluntarioUseCase enviarCredenciaisVoluntarioUseCase;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public CadastrarVoluntarioPrimeiraFaseUseCaseImpl(
            UsuarioRepository usuarioRepository,
            FichaRepository fichaRepository,
            EnviarCredenciaisVoluntarioUseCase enviarCredenciaisVoluntarioUseCase,
            PasswordEncoderGateway passwordEncoderGateway) {
        this.usuarioRepository = usuarioRepository;
        this.fichaRepository = fichaRepository;
        this.enviarCredenciaisVoluntarioUseCase = enviarCredenciaisVoluntarioUseCase;
        this.passwordEncoderGateway = passwordEncoderGateway;
        logger.info("CadastrarVoluntarioPrimeiraFaseUseCaseImpl inicializado com sucesso");
    }

    @Override
    public UsuarioPrimeiraFaseOutput executar(UsuarioInputPrimeiraFase usuarioInputPrimeiraFase) {
        logger.info("Iniciando cadastro de voluntário - primeira fase para email: {}", 
                   usuarioInputPrimeiraFase != null ? usuarioInputPrimeiraFase.getEmail() : "null");
        
        try {
            logger.debug("Validando dados de entrada do usuário");
            
            if (usuarioInputPrimeiraFase == null) {
                logger.error("Dados de entrada são nulos");
                throw new IllegalArgumentException("Dados de entrada não podem ser nulos");
            }
            
            if (usuarioRepository.findByEmail(usuarioInputPrimeiraFase.getEmail()).isPresent()) {
                logger.warn("Usuário com email {} já existe", usuarioInputPrimeiraFase.getEmail());
                throw new IllegalArgumentException("Usuário com este email já existe");
            }
            
            Ficha ficha = new Ficha();
            ficha.setNome(usuarioInputPrimeiraFase.getNome());
            ficha.setSobrenome(usuarioInputPrimeiraFase.getSobrenome());
            ficha.setCpf(usuarioInputPrimeiraFase.getCpf());
            fichaRepository.save(ficha);
            
            Usuario usuario = Usuario.criarVoluntario(
                usuarioInputPrimeiraFase.getEmail(),
                    passwordEncoderGateway.encode(usuarioInputPrimeiraFase.getSenha()),
                ficha
            );
            usuario = usuarioRepository.save(usuario);
            
            logger.info("Voluntário cadastrado com sucesso (primeira fase) para email: {}", usuarioInputPrimeiraFase.getEmail());
            
            UsuarioPrimeiraFaseOutput resultado = new UsuarioPrimeiraFaseOutput();
            resultado.setIdUsuario(usuario.getIdUsuario());
            resultado.setNome(ficha.getNome());
            resultado.setSobrenome(ficha.getSobrenome());
            resultado.setEmail(usuario.getEmail());
            resultado.setCpf(ficha.getCpf());
            
            try {
                logger.info("Iniciando envio de credenciais por email para o voluntário: {}", usuarioInputPrimeiraFase.getEmail());
                
                String nomeCompleto = usuarioInputPrimeiraFase.getNome() + " " + usuarioInputPrimeiraFase.getSobrenome();
                String resultadoEmail = enviarCredenciaisVoluntarioUseCase.executar(
                    usuarioInputPrimeiraFase.getEmail(),
                    nomeCompleto,
                    usuarioInputPrimeiraFase.getSenha(),
                    usuario.getIdUsuario()
                );
                
                logger.info("Credenciais enviadas com sucesso para o voluntário: {}. Resultado: {}", 
                           usuarioInputPrimeiraFase.getEmail(), resultadoEmail);
                           
            } catch (Exception emailException) {
                logger.error("Erro ao enviar credenciais por email para o voluntário: {}. Erro: {}", 
                            usuarioInputPrimeiraFase.getEmail(), emailException.getMessage(), emailException);
            }
            
            return resultado;
            
        } catch (Exception e) {
            logger.error("Erro ao executar cadastro de voluntário - primeira fase para email: {}. Erro: {}", 
                        usuarioInputPrimeiraFase != null ? usuarioInputPrimeiraFase.getEmail() : "null", 
                        e.getMessage(), e);
            throw e;
        }
    }
}
