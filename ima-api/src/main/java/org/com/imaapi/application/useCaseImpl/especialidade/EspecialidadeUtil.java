package org.com.imaapi.application.useCaseImpl.especialidade;

import org.com.imaapi.application.dto.especialidade.input.EspecialidadeInput;
import org.com.imaapi.application.dto.especialidade.output.EspecialidadeOutput;
import org.com.imaapi.domain.model.Especialidade;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadeUtil {

    public void validarEspecialidadeInput(EspecialidadeInput especialidadeInput) {
        if (especialidadeInput == null) {
            throw new IllegalArgumentException("EspecialidadeInput não pode ser nulo");
        }
        
        if (especialidadeInput.getNome() == null || especialidadeInput.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da especialidade é obrigatório");
        }
        
        if (especialidadeInput.getNome().length() > 100) {
            throw new IllegalArgumentException("Nome da especialidade não pode ter mais de 100 caracteres");
        }
    }
    
    /**
     * Converter entidade Especialidade para DTO EspecialidadeOutput
     */
    public EspecialidadeOutput converterParaEspecialidadeOutput(Especialidade especialidade) {
        if (especialidade == null) return null;
        
        EspecialidadeOutput output = new EspecialidadeOutput();
        output.setId(especialidade.getIdEspecialidade());
        output.setNome(especialidade.getNome());
        return output;
    }
    
    /**
     * Converter DTO EspecialidadeInput para entidade Especialidade
     */
    public Especialidade converterParaEspecialidade(EspecialidadeInput especialidadeInput) {
        if (especialidadeInput == null) return null;
        
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(especialidadeInput.getNome().trim());
        return especialidade;
    }
    
    /**
     * Validar se ID é válido
     */
    public void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID deve ser um número positivo");
        }
    }
}