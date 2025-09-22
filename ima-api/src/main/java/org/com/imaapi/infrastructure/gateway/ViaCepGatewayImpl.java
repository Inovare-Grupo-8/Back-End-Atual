package org.com.imaapi.infrastructure.gateway;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.domain.gateway.ViaCepGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Implementação temporária do ViaCepGateway para resolver dependência.
 * NOTA: Esta é uma implementação provisória para a camada de aplicação funcionar.
 */
@Component
public class ViaCepGatewayImpl implements ViaCepGateway {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ViaCepGatewayImpl.class);
    private static final String VIA_CEP_API = "https://viacep.com.br/ws/%s/json/";
    
    private final RestTemplate restTemplate;
    
    public ViaCepGatewayImpl() {
        this.restTemplate = new RestTemplate();
    }
    
    @Override
    public EnderecoOutput buscarPorCep(String cep) {
        try {
            LOGGER.info("Buscando CEP {} na API ViaCEP", cep);
            
            String url = String.format(VIA_CEP_API, cep);
            EnderecoOutput resultado = restTemplate.getForObject(url, EnderecoOutput.class);
            
            if (resultado != null && resultado.getCep() != null) {
                LOGGER.info("CEP {} encontrado com sucesso", cep);
                return resultado;
            } else {
                LOGGER.warn("CEP {} não encontrado na API ViaCEP", cep);
                return null;
            }
            
        } catch (Exception e) {
            LOGGER.error("Erro ao buscar CEP {} na API ViaCEP: {}", cep, e.getMessage());
            throw new RuntimeException("Erro ao consultar API ViaCEP: " + e.getMessage(), e);
        }
    }
}