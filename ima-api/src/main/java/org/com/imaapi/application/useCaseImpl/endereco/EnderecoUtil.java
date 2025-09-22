package org.com.imaapi.application.useCaseImpl.endereco;

import org.com.imaapi.application.dto.usuario.output.EnderecoOutput;
import org.com.imaapi.domain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoUtil {

    // Formatar CEP removendo caracteres não numéricos
    public String formatarCep(String cep) {
        if (cep == null) return null;
        return cep.replaceAll("\\D", "");
    }
    
    // Validar se CEP está no formato correto (8 dígitos)
    public boolean isValidCep(String cep) {
        return cep != null && cep.matches("\\d{8}");
    }
    
    // Converter entidade Endereco para DTO EnderecoOutput
    public EnderecoOutput converterParaEnderecoOutput(Endereco endereco) {
        if (endereco == null) return null;
        
        EnderecoOutput output = new EnderecoOutput();
        output.setCep(endereco.getCep());
        output.setLogradouro(endereco.getLogradouro());
        output.setBairro(endereco.getBairro());
        output.setNumero(endereco.getNumero());
        output.setUf(endereco.getUf());
        output.setLocalidade(endereco.getCidade());
        output.setComplemento(endereco.getComplemento());
        return output;
    }
    
    // Validar se EnderecoOutput possui dados mínimos necessários
    public void validarEnderecoOutput(EnderecoOutput enderecoOutput) {
        if (enderecoOutput == null || enderecoOutput.getCep() == null) {
            throw new IllegalArgumentException("EndereçoOutput inválido para cadastro");
        }
        
        String cep = formatarCep(enderecoOutput.getCep());
        if (!isValidCep(cep)) {
            throw new IllegalArgumentException("CEP inválido. Deve conter 8 dígitos.");
        }
    }
}