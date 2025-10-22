package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.dto.usuario.output.AssistenteSocialOutput;
import org.com.imaapi.application.useCase.usuario.BuscarAssistenteSocialUseCase;
import org.com.imaapi.application.useCaseImpl.endereco.EnderecoUtil;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Telefone;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.VoluntarioEspecialidade;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.com.imaapi.domain.repository.VoluntarioEspecialidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BuscarAssistenteSocialUseCaseImpl implements BuscarAssistenteSocialUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioEspecialidadeRepository voluntarioEspecialidadeRepository;
    private final EnderecoUtil enderecoUtil;

    public BuscarAssistenteSocialUseCaseImpl(
            UsuarioRepository usuarioRepository,
            TelefoneRepository telefoneRepository,
            VoluntarioRepository voluntarioRepository,
            VoluntarioEspecialidadeRepository voluntarioEspecialidadeRepository,
            EnderecoUtil enderecoUtil) {
        this.usuarioRepository = usuarioRepository;
        this.telefoneRepository = telefoneRepository;
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioEspecialidadeRepository = voluntarioEspecialidadeRepository;
        this.enderecoUtil = enderecoUtil;
    }

    @Override
    public AssistenteSocialOutput executar(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioOpt.get();
        Ficha ficha = usuario.getFicha();
        if (ficha == null) {
            return null;
        }

        AssistenteSocialOutput output = new AssistenteSocialOutput();
        output.setIdUsuario(usuario.getIdUsuario());
        output.setNome(ficha.getNome());
        output.setSobrenome(ficha.getSobrenome());
        output.setEmail(usuario.getEmail());

        // Buscar dados do voluntário
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
        if (voluntario != null) {
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
        }

        // Foto URL do usuário
        output.setFotoUrl(usuario.getFotoUrl());

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

        return output;
    }
}