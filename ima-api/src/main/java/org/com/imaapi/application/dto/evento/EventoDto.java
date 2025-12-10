package org.com.imaapi.application.dto.evento;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoDto {
    private String titulo;
    private String descricao;
    private LocalDateTime inicio;
    private LocalDateTime fim;
}
