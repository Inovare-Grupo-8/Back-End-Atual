package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.useCase.usuario.CadastrarUsuarioSegundaFaseUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarUsuarioSegundaFaseUseCaseImpl implements CadastrarUsuarioSegundaFaseUseCase {

    private final UsuarioRepository usuarioRepository;

    public CadastrarUsuarioSegundaFaseUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioOutput executar(Integer idUsuario, UsuarioInputSegundaFase usuarioInputSegundaFase) {
        if (idUsuario == null || usuarioInputSegundaFase == null) {
            return null;
        }

        return usuarioRepository.findById(idUsuario)
                .map(usuario -> {

                    BeanUtils.copyProperties(usuarioInputSegundaFase, usuario, "id", "email");
                    Usuario salvo = usuarioRepository.save(usuario);

                    return new UsuarioOutput(
                        usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getFotoUrl(),
                        usuario.getDataCadastro(),
                        usuario.getTipo()
                    );
                })
                .orElse(null);
    }
}
