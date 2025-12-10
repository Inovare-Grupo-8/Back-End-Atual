package org.com.imaapi.application.dto.usuario.output;

import jakarta.persistence.Table;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Table(name = "enderecos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoOutput {
    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;
    
    // Campo para detectar CEPs inexistentes (usado internamente)
    private Boolean erro;
}