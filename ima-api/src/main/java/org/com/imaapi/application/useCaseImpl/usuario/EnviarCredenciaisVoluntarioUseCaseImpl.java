package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.email.EnviarEmailUseCase;
import org.com.imaapi.application.useCase.usuario.EnviarCredenciaisVoluntarioUseCase;
import org.com.imaapi.application.useCaseImpl.email.EnviarEmaiUseCaseImpl;
import org.com.imaapi.application.dto.email.EmailDto;
import org.springframework.stereotype.Service;

@Service
public class EnviarCredenciaisVoluntarioUseCaseImpl implements EnviarCredenciaisVoluntarioUseCase {

    private final EnviarEmaiUseCaseImpl enviarEmaiUseCaseImpl;

    public EnviarCredenciaisVoluntarioUseCaseImpl(EnviarEmaiUseCaseImpl enviarEmaiUseCaseImpl) {
        this.enviarEmaiUseCaseImpl = enviarEmaiUseCaseImpl;
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
        EmailDto emailDto = new EmailDto(email, nome, assunto, email, senha);

        enviarEmaiUseCaseImpl.enviarEmail(emailDto);

        return "Credenciais enviadas com sucesso.";
    }
}
