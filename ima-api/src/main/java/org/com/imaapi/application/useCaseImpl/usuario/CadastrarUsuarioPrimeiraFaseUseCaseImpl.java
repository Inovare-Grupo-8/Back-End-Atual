package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarUsuarioPrimeiraFaseUseCase;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioPrimeiraFaseOutput executar(UsuarioInputPrimeiraFase input) {
        LOGGER.info("Iniciando cadastro de usuário (primeira fase) para email: {}", input.getEmail());

        // Verificar se o usuário já existe
        if (usuarioRepository.findByEmail(input.getEmail()).isPresent()) {
            LOGGER.warn("Usuário com email {} já existe", input.getEmail());
            throw new IllegalArgumentException("Usuário com este email já existe");
        }

        // Criar ficha
        Ficha ficha = new Ficha();
        ficha.setNome(input.getNome());
        ficha.setSobrenome(input.getSobrenome());
        ficha.setCpf(input.getCpf());
        fichaRepository.save(ficha);

        // Criar usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(input.getEmail());
        usuario.setSenha(input.getSenha());
        usuario.setTipo(TipoUsuario.ASSISTIDO);
        usuario.setFicha(ficha);
        usuarioRepository.save(usuario);

        LOGGER.info("Usuário cadastrado com sucesso (primeira fase) para email: {}", input.getEmail());

        // Criar output
        UsuarioPrimeiraFaseOutput output = new UsuarioPrimeiraFaseOutput();
        output.setIdUsuario(usuario.getIdUsuario());
        output.setNome(ficha.getNome());
        output.setSobrenome(ficha.getSobrenome());
        output.setEmail(usuario.getEmail());
        output.setCpf(ficha.getCpf());
        output.setDataNascimento(ficha.getDtNascim());

        return output;
    }
}