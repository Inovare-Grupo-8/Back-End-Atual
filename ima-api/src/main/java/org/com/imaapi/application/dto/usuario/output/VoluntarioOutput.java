package org.com.imaapi.application.dto.usuario.output;

import lombok.Data;
import org.com.imaapi.domain.model.enums.Funcao;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VoluntarioOutput {
    
    private Integer id;
    private Funcao funcao;
    private LocalDate dataCadastro;
    private String biografiaProfissional;
    private String registroProfissional;
    private Integer usuarioId;
    private String usuarioNome;
    private String usuarioEmail;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public VoluntarioOutput() {}
    
    public VoluntarioOutput(Integer id, Funcao funcao, LocalDate dataCadastro, 
                           String biografiaProfissional, String registroProfissional,
                           Integer usuarioId, String usuarioNome, String usuarioEmail,
                           LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.funcao = funcao;
        this.dataCadastro = dataCadastro;
        this.biografiaProfissional = biografiaProfissional;
        this.registroProfissional = registroProfissional;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.usuarioEmail = usuarioEmail;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}