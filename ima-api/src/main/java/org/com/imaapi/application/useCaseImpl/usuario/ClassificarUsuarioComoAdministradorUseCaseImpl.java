package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.com.imaapi.application.useCase.usuario.ClassificarUsuarioComoAdministradorUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClassificarUsuarioComoAdministradorUseCaseImpl implements ClassificarUsuarioComoAdministradorUseCase {

    private final UsuarioRepository usuarioRepository;

    public ClassificarUsuarioComoAdministradorUseCaseImpl(UsuarioRepository usuarioRepository) {
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
                    usuario.atualizarTipo(TipoUsuario.ADMINISTRADOR);

                    Usuario salvo = usuarioRepository.save(usuario);

                    UsuarioListarOutput output = new UsuarioListarOutput();
                    output.setIdUsuario(salvo.getIdUsuario());
                    output.setEmail(salvo.getEmail());
                    output.setTipo(salvo.getTipo());
                    if (salvo.getFicha() != null) {
                        output.setNome(salvo.getFicha().getNome());
                    }
                    return output;
                })
                .orElse(null);
    }
}
