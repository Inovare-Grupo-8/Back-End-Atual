package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;
import org.com.imaapi.application.useCase.usuario.BuscarDadosPrimeiraFaseUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuscarDadosPrimeiraFaseUseCaseImpl implements BuscarDadosPrimeiraFaseUseCase {

    private final UsuarioRepository usuarioRepository;

    public BuscarDadosPrimeiraFaseUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioPrimeiraFaseOutput executarPorId(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + idUsuario));
        UsuarioPrimeiraFaseOutput output = new UsuarioPrimeiraFaseOutput();
        BeanUtils.copyProperties(usuario, output);
        return output;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioPrimeiraFaseOutput executarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o email informado: " + email));
        UsuarioPrimeiraFaseOutput output = new UsuarioPrimeiraFaseOutput();
        BeanUtils.copyProperties(usuario, output);
        return output;
    }
}
