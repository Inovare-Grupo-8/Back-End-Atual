package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.ListarVoluntariosUseCase;
import org.com.imaapi.application.dto.usuario.output.VoluntarioListagemOutput;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("usuarioListarVoluntariosUseCaseImpl")
public class ListarVoluntariosUseCaseImpl implements ListarVoluntariosUseCase {

    private final UsuarioRepository usuarioRepository;

    public ListarVoluntariosUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<VoluntarioListagemOutput> executar() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getTipo() == TipoUsuario.VOLUNTARIO)
                .map(usuario -> {
                    VoluntarioListagemOutput dto = new VoluntarioListagemOutput();
                    dto.setIdUsuario(usuario.getIdUsuario());
                    dto.setEmail(usuario.getEmail());
                    dto.setDataCadastro(usuario.getDataCadastro());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
