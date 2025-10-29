package org.com.imaapi.application.useCaseImpl.email;

import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.email.EmailDto;
import org.com.imaapi.application.dto.email.EmailQueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailQueueProducerImpl {

    private static final Logger logger = LoggerFactory.getLogger(EmailQueueProducerImpl.class);
    
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${email.queue.name:fila_email}")
    private String emailQueueName;
    
    @Value("${email.exchange.name:email-exchange}")
    private String emailExchangeName;
    
    @Value("${email.routing.key:email}")
    private String emailRoutingKey;
    
    public void enviarEmailParaFila(EmailDto emailDto) {
        try {
            EmailQueueMessage message = new EmailQueueMessage(emailDto);
            
            logger.info("Enviando email para fila: destinatário={}, assunto={}", 
                       emailDto.getDestinatario(), emailDto.getAssunto());
            
            rabbitTemplate.convertAndSend(emailExchangeName, emailRoutingKey, message);
            
            logger.info("Email enviado para fila com sucesso: destinatário={}, assunto={}", 
                       emailDto.getDestinatario(), emailDto.getAssunto());
                       
        } catch (Exception e) {
            logger.error("Erro ao enviar email para fila: destinatário={}, assunto={}, erro={}", 
                        emailDto.getDestinatario(), emailDto.getAssunto(), e.getMessage(), e);
            throw new RuntimeException("Erro ao enviar email para fila", e);
        }
    }
}