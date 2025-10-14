package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.email.EnviarEmailUseCase;
import org.com.imaapi.application.useCase.usuario.EnviarCredenciaisVoluntarioUseCase;

public class EnviarCredenciaisVoluntarioUseCaseImpl implements EnviarCredenciaisVoluntarioUseCase {

    private final EnviarEmailUseCase emailService;

    public EnviarCredenciaisVoluntarioUseCaseImpl(EnviarEmailUseCase EmailUseCase, EnviarEmailUseCase emailService) {
        this.emailService = emailService;
    }

    @Override
    public String executar(String email, String nome, String senha) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        String assunto = "Seus dados de acesso";
        String corpoEmail = String.format(
                "Olá %s,\n\nSeu email é: %s\nSenha: %s\n\nAtenciosamente,\nEquipe IMAPI",
                nome, email, senha
        );

        emailService= emailService(email, assunto, corpoEmail);

        return "Credenciais enviadas com sucesso.";
    }
}
