package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.email.GerarConteudoHtmlCredenciaisVoluntarioUseCase;
import org.com.imaapi.application.useCase.usuario.EnviarCredenciaisVoluntarioUseCase;
import org.com.imaapi.application.useCaseImpl.email.EnviarEmaiUseCaseImpl;
import org.com.imaapi.application.dto.email.EmailDto;
import org.com.imaapi.application.useCaseImpl.email.EmailQueueProducerImpl;
import org.springframework.stereotype.Service;

@Service
public class EnviarCredenciaisVoluntarioUseCaseImpl implements EnviarCredenciaisVoluntarioUseCase {

    private final EnviarEmaiUseCaseImpl enviarEmaiUseCaseImpl;
    private final GerarConteudoHtmlCredenciaisVoluntarioUseCase gerarConteudoHtmlCredenciaisVoluntarioUseCase;
    private final EmailQueueProducerImpl emailQueueProducerImpl;

    public EnviarCredenciaisVoluntarioUseCaseImpl(
            EnviarEmaiUseCaseImpl enviarEmaiUseCaseImpl,
            GerarConteudoHtmlCredenciaisVoluntarioUseCase gerarConteudoHtmlCredenciaisVoluntarioUseCase,
            EmailQueueProducerImpl emailQueueProducerImpl) {
        this.enviarEmaiUseCaseImpl = enviarEmaiUseCaseImpl;
        this.gerarConteudoHtmlCredenciaisVoluntarioUseCase = gerarConteudoHtmlCredenciaisVoluntarioUseCase;
        this.emailQueueProducerImpl = emailQueueProducerImpl;
    }

    @Override
    public String executar(String email, String nome, String senha, Integer idUsuario) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        String dadosCredenciais = nome + "|" + email + "|" + senha;
        
        String assunto = "credenciais voluntario"; 
        EmailDto emailDto = new EmailDto(email, dadosCredenciais, assunto, email, senha, idUsuario);

        emailQueueProducerImpl.enviarEmailParaFila(emailDto);

        return "Credenciais enviadas para fila com sucesso.";
    }
}
