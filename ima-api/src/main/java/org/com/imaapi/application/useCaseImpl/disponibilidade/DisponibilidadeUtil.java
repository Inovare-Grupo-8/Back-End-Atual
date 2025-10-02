package org.com.imaapi.application.useCaseImpl.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.input.DisponibilidadeInput;
import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;
import org.com.imaapi.domain.model.Disponibilidade;
import org.com.imaapi.domain.model.Voluntario;
import org.springframework.stereotype.Component;

@Component
public class DisponibilidadeUtil {

    //Validar DisponibilidadeInput
    public void validarDisponibilidadeInput(DisponibilidadeInput disponibilidadeInput) {
        if (disponibilidadeInput == null) {
            throw new IllegalArgumentException("DisponibilidadeInput não pode ser nulo");
        }
        
        if (disponibilidadeInput.getDataHorario() == null) {
            throw new IllegalArgumentException("Data e horário da disponibilidade são obrigatórios");
        }
        
        if (disponibilidadeInput.getUsuarioId() == null || disponibilidadeInput.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser um número positivo");
        }
    }
    
    //Converter entidade Disponibilidade para DTO DisponibilidadeOutput
    public DisponibilidadeOutput converterParaDisponibilidadeOutput(Disponibilidade disponibilidade) {
        if (disponibilidade == null) return null;
        
        DisponibilidadeOutput output = new DisponibilidadeOutput();
        output.setId(disponibilidade.getIdDisponibilidade());
        output.setDataHorario(disponibilidade.getDataHorario());
        output.setCriadoEm(disponibilidade.getCriadoEm());
        output.setAtualizadoEm(disponibilidade.getAtualizadoEm());
        
        // Dados do voluntário, se disponível
        if (disponibilidade.getVoluntario() != null) {
            Voluntario voluntario = disponibilidade.getVoluntario();
            output.setVoluntarioId(voluntario.getIdVoluntario());
            
            // Nome do voluntário através da ficha do usuário
            if (voluntario.getUsuario() != null && voluntario.getUsuario().getFicha() != null) {
                String nomeCompleto = voluntario.getUsuario().getFicha().getNome();
                if (voluntario.getUsuario().getFicha().getSobrenome() != null) {
                    nomeCompleto += " " + voluntario.getUsuario().getFicha().getSobrenome();
                }
                output.setVoluntarioNome(nomeCompleto);
            }
        }
        
        return output;
    }
    
    //Converter DTO DisponibilidadeInput para entidade Disponibilidade
    public Disponibilidade converterParaDisponibilidade(DisponibilidadeInput disponibilidadeInput) {
        if (disponibilidadeInput == null) return null;
        
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setDataHorario(disponibilidadeInput.getDataHorario());
        return disponibilidade;
    }
    
    //Validar se ID é válido
    public void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID deve ser um número positivo");
        }
    }
}