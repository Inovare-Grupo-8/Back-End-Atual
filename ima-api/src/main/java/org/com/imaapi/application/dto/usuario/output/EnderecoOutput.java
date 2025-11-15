package org.com.imaapi.application.dto.usuario.output;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "enderecos")
public class EnderecoOutput {
    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;
}