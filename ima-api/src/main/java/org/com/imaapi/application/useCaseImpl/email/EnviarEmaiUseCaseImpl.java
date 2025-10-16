package org.com.imaapi.application.useCaseImpl.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.email.EmailDto;
import org.com.imaapi.application.useCase.email.EnviarEmailUseCase;
import org.com.imaapi.application.useCase.email.GerarConteudoHtmlContinuarCadastroUseCase;
import org.com.imaapi.application.useCase.email.GerarConteudoHtmlBemVindoUseCase;
import org.com.imaapi.application.useCase.email.GerarConteudoHtmlBemVindoVoluntarioUseCase;
import org.com.imaapi.application.useCase.email.GerarConteudoHtmlAgendamentoUseCase;
import org.com.imaapi.application.useCase.email.GerarConteudoHtmlCredenciaisVoluntarioUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarEmaiUseCaseImpl implements EnviarEmailUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EnviarEmaiUseCaseImpl.class);
    private final JavaMailSender javaMailSender;
    private final GerarConteudoHtmlContinuarCadastroUseCase gerarContinuarCadastro;
    private final GerarConteudoHtmlBemVindoUseCase gerarBemVindo;
    private final GerarConteudoHtmlBemVindoVoluntarioUseCase gerarBemVindoVoluntario;
    private final GerarConteudoHtmlAgendamentoUseCase gerarAgendamento;
    private final GerarConteudoHtmlCredenciaisVoluntarioUseCase gerarCredenciaisVoluntario;

    @Value("${spring.mail.username}")
    private String remetente;

    @Override
    public String enviarEmail(EmailDto dto) {
        if (dto.getDestinatario() == null || dto.getDestinatario().isEmpty()) {
            logger.error("O destinatário do e-mail está vazio ou nulo.");
            return "Erro: O destinatário do e-mail não pode ser vazio ou nulo.";
        }

        logger.info("Enviando e-mail para: {}", dto.getDestinatario());
        int maxTentativas = 3;
        Exception ultimaExcecao = null;

        for (int tentativa = 1; tentativa <= maxTentativas; tentativa++) {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom(remetente);
                helper.setTo(dto.getDestinatario());

                String htmlContent;
                switch (dto.getAssunto()) {
                    case "continuar cadastro":
                        logger.info("Enviando email para continuar cadastro");
                        helper.setSubject("Complete seu cadastro no IMA!");
                        String[] parts = dto.getNome().split("\\|");
                        String nomeUsuario = parts[0];
                        Integer idUsuario = Integer.parseInt(parts[1]);
                        htmlContent = gerarContinuarCadastro.gerar(nomeUsuario, idUsuario);
                        break;
                    case "bem vindo":
                        logger.info("Enviando email de boas-vindas - usuário comum");
                        helper.setSubject("Bem-vindo ao IMA!");
                        htmlContent = gerarBemVindo.gerar(dto.getNome());
                        break;
                    case "bem vindo voluntario":
                        logger.info("Enviando email de boas-vindas - voluntário");
                        helper.setSubject("Seja bem-vindo ao IMA Voluntário!");
                        htmlContent = gerarBemVindoVoluntario.gerar(dto.getNome());
                        break;
                    case "credenciais voluntario":
                        logger.info("Enviando credenciais para voluntário");
                        helper.setSubject("Suas credenciais de acesso - IMA Voluntário");
                        String[] credenciais = dto.getNome().split("\\|");
                        String nomeVoluntario = credenciais[0];
                        String emailVoluntario = credenciais[1];
                        String senhaVoluntario = credenciais[2];
                        htmlContent = gerarCredenciaisVoluntario.gerar(nomeVoluntario, emailVoluntario, senhaVoluntario);
                        break;
                    case "agendamento realizado":
                        logger.info("Enviando email agendamento de voluntario");
                        helper.setSubject("Agendamento Realizado");
                        htmlContent = gerarAgendamento.gerar(dto.getNome(), "Agendamento Realizado");
                        break;
                    case "agendamento cancelado":
                        logger.info("Enviando email agendamento cancelado");
                        helper.setSubject("Agendamento Cancelado");
                        htmlContent = gerarAgendamento.gerar(dto.getNome(), "Agendamento Cancelado");
                        break;
                    default:
                        logger.info("Assunto não encontrado");
                        return "Erro: Assunto não encontrado.";
                }

                helper.setText(htmlContent, true);
                javaMailSender.send(mimeMessage);
                logger.info("E-mail enviado com sucesso para: {} na tentativa {}", dto.getDestinatario(), tentativa);
                return "E-mail enviado com sucesso!";
            } catch (MessagingException e) {
                ultimaExcecao = e;
                logger.warn("Tentativa {} falhou ao enviar e-mail para {}: {}", tentativa, dto.getDestinatario(), e.getMessage());
                if (tentativa < maxTentativas) {
                    try {
                        Thread.sleep(2000 * tentativa);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        logger.error("Thread interrompida durante espera entre tentativas");
                        break;
                    }
                }
            }
        }
        logger.error("Falha ao enviar e-mail para {} após {} tentativas: {}", dto.getDestinatario(), maxTentativas, ultimaExcecao != null ? ultimaExcecao.getMessage() : "Erro desconhecido");
        return "Erro ao enviar e-mail após " + maxTentativas + " tentativas: " + (ultimaExcecao != null ? ultimaExcecao.getMessage() : "Erro desconhecido");
    }
}
