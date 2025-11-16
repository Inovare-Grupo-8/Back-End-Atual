package org.com.imaapi.application.dto.usuario.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.com.imaapi.domain.model.enums.TipoUsuario;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioNaoClassificadoOutput {
    private Integer id;
    private String email;
    private TipoUsuario tipo;
    private LocalDate dataCadastro;
    private String nome;
    private String sobrenome;
    private String cpf;
}