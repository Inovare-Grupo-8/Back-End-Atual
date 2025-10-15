package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarVoluntarioSegundaFaseUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarVoluntarioSegundaFaseUseCaseImpl implements CadastrarVoluntarioSegundaFaseUseCase {

    private final UsuarioRepository usuarioRepository;

    public CadastrarVoluntarioSegundaFaseUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioOutput executar(Integer idUsuario, UsuarioInputSegundaFase usuarioInputSegundaFase) {
        if (usuarioInputSegundaFase == null || idUsuario == null) {
            return null;
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return null;
        }
        BeanUtils.copyProperties(usuarioInputSegundaFase, usuario);
        Usuario salvo = usuarioRepository.save(usuario);
        UsuarioOutput output = new UsuarioOutput();
        BeanUtils.copyProperties(salvo, output);
        return output;
    }
}
