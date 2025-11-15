package org.com.imaapi.application.useCase.usuario;

public interface EnviarCredenciaisVoluntarioUseCase {
    String executar(String email, String nome, String senha);
}