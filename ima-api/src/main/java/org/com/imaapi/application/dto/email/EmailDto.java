package org.com.imaapi.application.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String destinatario;
    private String nome;
    private String assunto;
    private String emailVoluntario;
    private String senha;
    private Integer idUsuario;
}