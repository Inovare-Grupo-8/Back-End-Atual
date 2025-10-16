package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.com.imaapi.application.useCase.usuario.ClassificarUsuarioComoGratuidadeUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClassificarUsuarioComoGratuidadeUseCaseImpl implements ClassificarUsuarioComoGratuidadeUseCase {
    private final UsuarioRepository usuarioRepository;

    public ClassificarUsuarioComoGratuidadeUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioListarOutput executar(Integer id) {
        if (id == null) {
            return null;
        }

        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.atualizarTipo(TipoUsuario.GRATUIDADE);

                    Usuario salvo = usuarioRepository.save(usuario);

                    UsuarioListarOutput output = new UsuarioListarOutput();
                    BeanUtils.copyProperties(salvo, output);
                    return output;
                })
                .orElse(null);
    }
}
