package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioClassificacaoOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuariosNaoClassificadosUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuscarUsuariosNaoClassificadosUseCaseImpl implements BuscarUsuariosNaoClassificadosUseCase {

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuariosNaoClassificadosUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioClassificacaoOutput> executar() {
        return usuarioRepository.findAll().stream()
                .filter(this::isNaoClassificado)
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    private boolean isNaoClassificado(Usuario usuario) {
        return usuario.getClassificacao() == null;
    }

    private UsuarioClassificacaoOutput toOutput(Usuario usuario) {
        UsuarioClassificacaoOutput output = new UsuarioClassificacaoOutput();
        BeanUtils.copyProperties(usuario, output);
        return output;
    }
}
