package org.com.imaapi.application.dto.disponibilidade.input;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisponibilidadeInput {
    
    private LocalDateTime dataHorario;
    private Integer usuarioId;
    
    public DisponibilidadeInput() {}
    
    public DisponibilidadeInput(LocalDateTime dataHorario, Integer usuarioId) {
        this.dataHorario = dataHorario;
        this.usuarioId = usuarioId;
    }
}