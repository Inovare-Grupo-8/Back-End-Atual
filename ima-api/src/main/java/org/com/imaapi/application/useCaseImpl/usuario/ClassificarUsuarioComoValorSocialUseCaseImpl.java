
package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.com.imaapi.application.useCase.usuario.ClassificarUsuarioComoValorSocialUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Service
public class ClassificarUsuarioComoValorSocialUseCaseImpl implements ClassificarUsuarioComoValorSocialUseCase {

    private final UsuarioRepository usuarioRepository;

    public ClassificarUsuarioComoValorSocialUseCaseImpl(UsuarioRepository usuarioRepository) {
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
                    applyValorSocialIfPossible(usuario);

                    Usuario salvo = usuarioRepository.save(usuario);

                    UsuarioListarOutput output = new UsuarioListarOutput();
                    output.setIdUsuario(salvo.getIdUsuario());
                    output.setEmail(salvo.getEmail());
                    output.setTipo(salvo.getTipo());
                    
                    // Incluir o nome da ficha se existir
                    if (salvo.getFicha() != null) {
                        output.setNome(salvo.getFicha().getNome());
                    }
                    
                    return output;
                })
                .orElse(null);
    }

    private void applyValorSocialIfPossible(Usuario usuario) {
        String[] candidateSetters = {
                "setValorSocial",
                "setEhValorSocial",
                "setIsValorSocial",
                "setValorSocialAplicado",
                "setValorSocialFlag",
                "setValorSocializado"
        };

        for (String setterName : candidateSetters) {
            try {
                Method m = usuario.getClass().getMethod(setterName, boolean.class);
                m.invoke(usuario, true);
                return;
            } catch (NoSuchMethodException ignored) {
                try {
                    Method m = usuario.getClass().getMethod(setterName, Boolean.class);
                    m.invoke(usuario, Boolean.TRUE);
                    return;
                } catch (NoSuchMethodException ignored2) {
                } catch (Exception ignored2) {
                }
            } catch (Exception ignored) {
            }
        }
    }
}
