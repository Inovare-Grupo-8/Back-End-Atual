package org.com.imaapi.infrastructure.gateway;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepClient {
    
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json";
    
    @Autowired
    private RestTemplate restTemplate;
    
    public ResponseEntity<EnderecoOutput> buscaEndereco(String cep, String numero, String complemento) {
        return restTemplate.getForEntity(VIA_CEP_URL, EnderecoOutput.class, cep);
    }
}