package org.com.imaapi.application.useCaseImpl.usuario;


import org.com.imaapi.application.dto.usuario.input.UsuarioInputSegundaFase;
import org.com.imaapi.application.dto.usuario.output.UsuarioListarOutput;
import org.com.imaapi.application.useCase.usuario.AtualizarUsuarioUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarUsuarioUseCaseImpl implements AtualizarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public AtualizarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioListarOutput executar(Integer id, UsuarioInputSegundaFase usuarioInputSegundaFase) {
        if (usuarioInputSegundaFase == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));

        BeanUtils.copyProperties(usuarioInputSegundaFase, usuario, "id");

        Usuario salvo = usuarioRepository.save(usuario);

        UsuarioListarOutput output = new UsuarioListarOutput();
        BeanUtils.copyProperties(salvo, output);

        return output;
    }
}
