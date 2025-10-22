package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioPrimeiraFaseOutput;
import org.com.imaapi.application.useCase.usuario.BuscarDadosPrimeiraFaseUseCase;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.repository.UsuarioRepository;
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
        return toOutput(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioPrimeiraFaseOutput executarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o email informado: " + email));
        return toOutput(usuario);
    }
    
    private UsuarioPrimeiraFaseOutput toOutput(Usuario usuario) {
        UsuarioPrimeiraFaseOutput output = new UsuarioPrimeiraFaseOutput();
        
        // Mapear dados básicos do usuário
        output.setIdUsuario(usuario.getIdUsuario());
        output.setEmail(usuario.getEmail());
        
        // Mapear dados da ficha se existir
        Ficha ficha = usuario.getFicha();
        if (ficha != null) {
            output.setNome(ficha.getNome());
            output.setSobrenome(ficha.getSobrenome());
            output.setCpf(ficha.getCpf());
        }
        
        return output;
    }
}
