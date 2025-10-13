package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.VoluntarioListagemOutput;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class ListarVoluntariosUseCaseImpl implements ListarVoluntariosUseCase {

    private final UsuarioRepository usuarioRepository;

    public ListarVoluntariosUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<VoluntarioListagemOutput> executar() {
        List<Usuario> voluntarios = usuarioRepository.findAllVoluntarios();

        return voluntarios.stream()
                .map(this::converterParaOutput)
                .collect(Collectors.toList());
    }

    private VoluntarioListagemOutput converterParaOutput(Usuario usuario) {
        return new VoluntarioListagemOutput(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getDataCadastro()
        );
    }
}
