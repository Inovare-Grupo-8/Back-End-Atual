package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioNaoClassificadoOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuariosNaoClassificadosUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.com.imaapi.domain.repository.UsuarioRepository;
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
    public List<UsuarioNaoClassificadoOutput> executar() {
        return usuarioRepository.findAll().stream()
                .filter(this::isNaoClassificado)
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    private boolean isNaoClassificado(Usuario usuario) {
        return usuario.getTipo() == TipoUsuario.NAO_CLASSIFICADO;
    }

    private UsuarioNaoClassificadoOutput toOutput(Usuario usuario) {
        UsuarioNaoClassificadoOutput output = new UsuarioNaoClassificadoOutput();
        
        // Mapear apenas campos essenciais (não nulos para usuários não classificados)
        output.setId(usuario.getIdUsuario());
        output.setEmail(usuario.getEmail());
        output.setTipo(usuario.getTipo());
        output.setDataCadastro(usuario.getDataCadastro());
        
        // Mapear dados básicos da ficha se existir
        Ficha ficha = usuario.getFicha();
        if (ficha != null) {
            output.setNome(ficha.getNome());
            output.setSobrenome(ficha.getSobrenome());
            output.setCpf(ficha.getCpf());
        }
        
        return output;
    }
}
