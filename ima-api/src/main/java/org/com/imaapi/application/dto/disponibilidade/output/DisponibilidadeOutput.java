package org.com.imaapi.application.dto.disponibilidade.output;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisponibilidadeOutput {
    
    private Integer id;
    private LocalDateTime dataHorario;
    private Integer voluntarioId;
    private String voluntarioNome;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public DisponibilidadeOutput() {}
    
    public DisponibilidadeOutput(Integer id, LocalDateTime dataHorario, Integer voluntarioId, 
                                String voluntarioNome, LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.dataHorario = dataHorario;
        this.voluntarioId = voluntarioId;
        this.voluntarioNome = voluntarioNome;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}