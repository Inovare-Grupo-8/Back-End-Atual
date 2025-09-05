package org.com.imaapi.domain.model.usuario.UsuarioOutputDTO;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "enderecos")
public class EnderecoOutputDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;
}