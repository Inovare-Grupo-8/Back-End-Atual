package org.com.imaapi.application.service.email;

import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.dto.email.EmailQueueMessage;
import org.com.imaapi.application.useCase.email.EnviarEmailUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailQueueConsumer.class);
    
    private final EnviarEmailUseCase enviarEmailUseCase;
    
    @RabbitListener(queues = "${email.queue.name:email-queue}")
    public void processarEmailDaFila(EmailQueueMessage message) {
        try {
            logger.info("Processando email da fila: destinatário={}, assunto={}", 
                       message.getDestinatario(), message.getAssunto());
            
            String resultado = enviarEmailUseCase.enviarEmail(message.toEmailDto());
            
            logger.info("Email processado com sucesso da fila: destinatário={}, assunto={}, resultado={}", 
                       message.getDestinatario(), message.getAssunto(), resultado);
                       
        } catch (Exception e) {
            logger.error("Erro ao processar email da fila: destinatário={}, assunto={}, erro={}", 
                        message.getDestinatario(), message.getAssunto(), e.getMessage(), e);
            
            // Aqui você pode implementar lógica de retry ou dead letter queue
            throw new RuntimeException("Erro ao processar email da fila", e);
        }
    }
}