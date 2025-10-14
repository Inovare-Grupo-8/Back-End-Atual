package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputPrimeiraFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarVoluntarioPrimeiraFaseUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarVoluntarioSegundaFaseUseCaseImpl implements CadastrarVoluntarioPrimeiraFaseUseCase {
    @Autowired


    public CadastrarVoluntarioSegundaFaseUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private final UsuarioRepository usuarioRepository;



    @Override
    @Transactional
    public UsuarioPrimeiraFaseOutput executar(UsuarioInputPrimeiraFase usuarioInputPrimeiraFase) {
        if (usuarioInputPrimeiraFase == null) {
            return null;
        }

        String email = usuarioInputPrimeiraFase.getEmail();
        if (email == null || email.isBlank()) {
            return null;
        }

        Usuario usuario = usuarioRepository.findByEmail(email).orElseGet(Usuario::new);

        BeanUtils.copyProperties(usuarioInputPrimeiraFase, usuario);

        Usuario salvo = usuarioRepository.save(usuario);

        UsuarioPrimeiraFaseOutput output = new UsuarioPrimeiraFaseOutput();
        BeanUtils.copyProperties(salvo, output);
        return output;
    }
}
