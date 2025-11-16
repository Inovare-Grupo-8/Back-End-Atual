package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.UsuarioOutput;
import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.application.dto.usuario.output.TelefoneOutput;
import org.com.imaapi.application.useCase.usuario.BuscarUsuarioPorNomeUseCase;
import org.com.imaapi.application.useCaseImpl.endereco.EnderecoUtil;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class BuscarUsuarioPorNomeUseCaseImpl implements BuscarUsuarioPorNomeUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final EnderecoUtil enderecoUtil;

    public BuscarUsuarioPorNomeUseCaseImpl(UsuarioRepository usuarioRepository, 
                                          TelefoneRepository telefoneRepository,
                                          VoluntarioRepository voluntarioRepository,
                                          EnderecoUtil enderecoUtil) {
        this.usuarioRepository = usuarioRepository;
        this.telefoneRepository = telefoneRepository;
        this.voluntarioRepository = voluntarioRepository;
        this.enderecoUtil = enderecoUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioOutput> executar(String nome) {
        return usuarioRepository.findByFichaNomeContainingIgnoreCase(nome).stream()
                .findFirst()
                .map(this::toOutput);
    }

    private UsuarioOutput toOutput(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        Ficha ficha = usuario.getFicha();
        if (ficha == null) {
            return null;
        }

        UsuarioOutput output = new UsuarioOutput();
        
        // Dados básicos do usuário (primeira fase)
        output.setId(usuario.getIdUsuario());
        output.setNome(ficha.getNome());
        output.setSobrenome(ficha.getSobrenome());
        output.setCpf(ficha.getCpf());
        output.setEmail(usuario.getEmail());
        output.setDataCadastro(usuario.getCriadoEm());
        
        // Dados da segunda fase
        output.setDataNascimento(ficha.getDtNascim());
        
        if (ficha.getRendaMinima() != null) {
            output.setRendaMinima(ficha.getRendaMinima().doubleValue());
        }
        
        if (ficha.getRendaMaxima() != null) {
            output.setRendaMaxima(ficha.getRendaMaxima().doubleValue());
        }
        
        if (ficha.getGenero() != null) {
            output.setGenero(ficha.getGenero().toString());
        }
        
        output.setTipo(usuario.getTipo());
        output.setAreaOrientacao(ficha.getAreaOrientacao());
        output.setComoSoube(ficha.getComoSoube());
        output.setProfissao(ficha.getProfissao());
        
        // Buscar dados do voluntário se aplicável
        if (usuario.getTipo() != null && usuario.getTipo().toString().equals("VOLUNTARIO")) {
            Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
            if (voluntario != null) {
                output.setFuncao(voluntario.getFuncao());
            }
        }
        
        // Buscar telefone
        List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
        if (!telefones.isEmpty()) {
            output.setTelefone(TelefoneOutput.fromEntity(telefones.get(0)));
        }
        
        // Buscar endereço
        if (ficha.getEndereco() != null) {
            output.setEndereco(enderecoUtil.converterParaEnderecoOutput(ficha.getEndereco()));
        }
        
        return output;
    }
}
