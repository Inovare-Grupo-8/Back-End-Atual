package org.com.imaapi.application.useCase.mensagem;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface RabbitConfigUseCase {
    RabbitTemplate getRabbitTemplate();
    String getQueueGratuidade();
    String getQueueAgendamentoGratuidade();
}