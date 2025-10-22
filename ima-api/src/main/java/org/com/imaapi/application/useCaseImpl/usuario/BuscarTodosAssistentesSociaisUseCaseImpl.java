package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.useCase.usuario.BuscarTodosAssistentesSociaisUseCase;
import org.com.imaapi.application.useCaseImpl.endereco.EnderecoUtil;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.VoluntarioEspecialidade;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.com.imaapi.domain.repository.VoluntarioEspecialidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BuscarTodosAssistentesSociaisUseCaseImpl implements BuscarTodosAssistentesSociaisUseCase {

    private final TelefoneRepository telefoneRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioEspecialidadeRepository voluntarioEspecialidadeRepository;
    private final EnderecoUtil enderecoUtil;

    public BuscarTodosAssistentesSociaisUseCaseImpl(
            TelefoneRepository telefoneRepository,
            VoluntarioRepository voluntarioRepository,
            VoluntarioEspecialidadeRepository voluntarioEspecialidadeRepository,
            EnderecoUtil enderecoUtil) {
        this.telefoneRepository = telefoneRepository;
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioEspecialidadeRepository = voluntarioEspecialidadeRepository;
        this.enderecoUtil = enderecoUtil;
    }

    @Override
    public List<AssistenteSocialOutput> executar() {
        // Buscar todos os voluntários que são assistentes sociais
        List<Voluntario> assistentesSociais = voluntarioRepository.findByFuncao("ASSISTENCIA_SOCIAL");
        
        return assistentesSociais.stream()
                .map(this::converterParaOutput)
                .collect(Collectors.toList());
    }

    private AssistenteSocialOutput converterParaOutput(Voluntario voluntario) {
        Usuario usuario = voluntario.getUsuario();
        Ficha ficha = usuario.getFicha();
        
        AssistenteSocialOutput output = new AssistenteSocialOutput();
        output.setIdUsuario(usuario.getIdUsuario());
        
        if (ficha != null) {
            output.setNome(ficha.getNome());
            output.setSobrenome(ficha.getSobrenome());
            
            // Buscar telefone
            List<Telefone> telefones = telefoneRepository.findByFichaIdFicha(ficha.getIdFicha());
            if (!telefones.isEmpty()) {
                Telefone telefone = telefones.get(0);
                String telefoneFormatado = String.format("(%s) %s-%s", telefone.getDdd(), telefone.getPrefixo(), telefone.getSufixo());
                output.setTelefone(telefoneFormatado);
            }
            
            // Buscar endereço
            if (ficha.getEndereco() != null) {
                output.setEndereco(enderecoUtil.converterParaEnderecoOutput(ficha.getEndereco()));
            }
        }
        
        output.setEmail(usuario.getEmail());
        
        // CRP do registro profissional
        output.setCrp(voluntario.getRegistroProfissional());
        
        // Especialidade principal do voluntário
        List<VoluntarioEspecialidade> especialidades = voluntarioEspecialidadeRepository.findByVoluntario(voluntario);
        Optional<VoluntarioEspecialidade> especialidadePrincipal = especialidades.stream()
                .filter(VoluntarioEspecialidade::getPrincipal)
                .findFirst();
        if (especialidadePrincipal.isPresent()) {
            output.setEspecialidade(especialidadePrincipal.get().getEspecialidade().getNome());
        }
        
        // Bio profissional
        output.setBio(voluntario.getBiografiaProfissional());
        
        // Foto URL do usuário
        output.setFotoUrl(usuario.getFotoUrl());
        
        return output;
    }
}