package org.com.imaapi.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.com.imaapi.domain.model.enums.TipoUsuario;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioOutput {
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private TipoUsuario tipo;
}
