package org.com.imaapi.application.useCaseImpl.voluntario;

import org.com.imaapi.application.dto.usuario.input.VoluntarioInput;
import org.com.imaapi.application.dto.usuario.output.VoluntarioOutput;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VoluntarioUtil {

    public void validarVoluntarioInput(VoluntarioInput voluntarioInput) {
        if (voluntarioInput == null) {
            throw new IllegalArgumentException("VoluntarioInput não pode ser nulo");
        }
        
        if (voluntarioInput.getFuncao() == null) {
            throw new IllegalArgumentException("Função do voluntário é obrigatória");
        }
        
        if (voluntarioInput.getFkUsuario() == null || voluntarioInput.getFkUsuario() <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser um número positivo");
        }
    }
    
    public VoluntarioOutput converterParaVoluntarioOutput(Voluntario voluntario) {
        if (voluntario == null) return null;
        
        VoluntarioOutput output = new VoluntarioOutput();
        output.setId(voluntario.getIdVoluntario());
        output.setFuncao(voluntario.getFuncao());
        output.setDataCadastro(voluntario.getDataCadastro());
        output.setBiografiaProfissional(voluntario.getBiografiaProfissional());
        output.setRegistroProfissional(voluntario.getRegistroProfissional());
        output.setCriadoEm(voluntario.getCriadoEm());
        output.setAtualizadoEm(voluntario.getAtualizadoEm());
        
        // Dados do usuário, se disponível
        if (voluntario.getUsuario() != null) {
            Usuario usuario = voluntario.getUsuario();
            output.setUsuarioId(usuario.getIdUsuario());
            output.setUsuarioEmail(usuario.getEmail());
            
            // Nome do usuário através da ficha
            if (usuario.getFicha() != null) {
                String nomeCompleto = usuario.getFicha().getNome();
                if (usuario.getFicha().getSobrenome() != null) {
                    nomeCompleto += " " + usuario.getFicha().getSobrenome();
                }
                output.setUsuarioNome(nomeCompleto);
            }
        }
        
        return output;
    }
    
    public Voluntario converterParaVoluntario(VoluntarioInput voluntarioInput, Usuario usuario) {
        if (voluntarioInput == null) return null;
        
        Voluntario voluntario = new Voluntario();
        voluntario.setFuncao(voluntarioInput.getFuncao());
        voluntario.setDataCadastro(LocalDate.now());
        voluntario.setFkUsuario(voluntarioInput.getFkUsuario());
        voluntario.setIdVoluntario(voluntarioInput.getFkUsuario());
        voluntario.setUsuario(usuario);
        
        return voluntario;
    }
    
    public void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID deve ser um número positivo");
        }
    }
}