package org.com.imaapi.application.useCaseImpl.email;

import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.email.EmailQueueMessage;
import org.com.imaapi.application.useCase.email.EnviarEmailUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailQueueConsumerImpl {

    private static final Logger logger = LoggerFactory.getLogger(EmailQueueConsumerImpl.class);
    
    private final EnviarEmailUseCase enviarEmailUseCase;
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${email.queue.name:fila_email}")
    private String emailQueueName;
    
    @Scheduled(fixedRate = 300000)
    public void processarFilaEmails() {
        logger.info("Iniciando processamento da fila de emails...");
        
        int emailsProcessados = 0;
        
        try {
            while (true) {
                Message message = rabbitTemplate.receive(emailQueueName, 1000); // timeout de 1 segundo
                
                if (message == null) {
                    break;
                }
                
                try {
                    EmailQueueMessage emailMessage = (EmailQueueMessage) rabbitTemplate.getMessageConverter().fromMessage(message);
                    
                    logger.info("Processando email da fila: destinatário={}, assunto={}", 
                               emailMessage.getDestinatario(), emailMessage.getAssunto());
                    
                    String resultado = enviarEmailUseCase.enviarEmail(emailMessage.toEmailDto());
                    
                    logger.info("Email processado com sucesso: destinatário={}, assunto={}, resultado={}", 
                               emailMessage.getDestinatario(), emailMessage.getAssunto(), resultado);
                    
                    emailsProcessados++;
                    
                } catch (Exception e) {
                    logger.error("Erro ao processar email individual da fila: {}", e.getMessage(), e);
                }
            }
            
            if (emailsProcessados > 0) {
                logger.info("Processamento da fila concluído. Total de emails processados: {}", emailsProcessados);
            } else {
                logger.debug("Nenhum email encontrado na fila para processar");
            }
            
        } catch (Exception e) {
            logger.error("Erro geral ao processar fila de emails: {}", e.getMessage(), e);
        }
    }
}