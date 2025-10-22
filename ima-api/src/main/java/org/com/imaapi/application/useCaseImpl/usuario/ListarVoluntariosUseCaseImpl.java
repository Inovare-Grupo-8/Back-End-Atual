package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.ListarVoluntariosUseCase;
import org.com.imaapi.application.dto.usuario.output.VoluntarioListagemOutput;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("usuarioListarVoluntariosUseCaseImpl")
public class ListarVoluntariosUseCaseImpl implements ListarVoluntariosUseCase {

    private final UsuarioRepository usuarioRepository;
    private final VoluntarioRepository voluntarioRepository;

    public ListarVoluntariosUseCaseImpl(UsuarioRepository usuarioRepository, VoluntarioRepository voluntarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.voluntarioRepository = voluntarioRepository;
    }

    @Override
    public List<VoluntarioListagemOutput> executar() {
        // Buscar todos os voluntários diretamente da tabela voluntario
        List<Voluntario> voluntarios = voluntarioRepository.findAll();
        
        return voluntarios.stream()
                .filter(voluntario -> voluntario.getUsuario() != null)
                .map(voluntario -> toOutput(voluntario.getUsuario(), voluntario))
                .collect(Collectors.toList());
    }
    
    private VoluntarioListagemOutput toOutput(Usuario usuario, Voluntario voluntario) {
        VoluntarioListagemOutput output = new VoluntarioListagemOutput();
        
        // Mapear dados básicos do usuário
        output.setIdUsuario(usuario.getIdUsuario());
        output.setEmail(usuario.getEmail());
        
        // Mapear dados do voluntário
        if (voluntario != null) {
            output.setIdVoluntario(voluntario.getIdVoluntario());
            // Usar o método getFuncaoString para obter o valor string diretamente
            output.setFuncao(voluntario.getFuncaoString());
            output.setDataCadastro(voluntario.getDataCadastro());
        }
        
        // Mapear dados do usuário para campos adicionais
        output.setUltimoAcesso(usuario.getUltimoAcesso());
        output.setAtivo(true); // Assumindo que usuários ativos estão na listagem
        
        // Mapear dados da ficha se existir
        Ficha ficha = usuario.getFicha();
        if (ficha != null) {
            output.setNome(ficha.getNome());
            output.setSobrenome(ficha.getSobrenome());
            output.setAreaOrientacao(ficha.getAreaOrientacao());
        }
        
        return output;
    }
}
