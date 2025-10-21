package org.com.imaapi.application.useCase.email;

public interface GerarConteudoHtmlCredenciaisVoluntarioUseCase {
    String gerar(String nome, String email, String senha, Integer idUsuario);
}