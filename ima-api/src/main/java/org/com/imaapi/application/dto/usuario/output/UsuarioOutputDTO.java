package org.com.imaapi.application.dto.usuario.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.com.imaapi.domain.model.enums.Funcao;
import org.com.imaapi.domain.model.enums.Genero;
import org.com.imaapi.domain.model.enums.TipoUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioOutputDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private String email;    private String senha;    private LocalDate dataNascimento;
    private Double rendaMinima;
    private Double rendaMaxima;
    private Genero genero;
    private TipoUsuario tipo;
    private Funcao funcao;
    private LocalDateTime dataCadastro;
    private EnderecoOutputDTO endereco;

    public UsuarioOutputDTO(String nome, String cpf, String email, LocalDate dataNascimento, TipoUsuario tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
    }
}