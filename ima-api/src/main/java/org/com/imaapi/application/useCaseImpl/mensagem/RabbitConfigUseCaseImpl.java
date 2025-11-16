package org.com.imaapi.application.useCaseImpl.mensagem;

import lombok.RequiredArgsConstructor;
import org.com.imaapi.application.useCase.mensagem.RabbitConfigUseCase;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitConfigUseCaseImpl implements RabbitConfigUseCase {

    private final ConnectionFactory connectionFactory;

    @Value("${broker.exchange.name}")
    private String exchangeName;

    @Value("${broker.queue.name1}")
    private String queueGratuidade;

    @Value("${broker.queue.name2}")
    private String queueAgendamentoGratuidade;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue fila1() {
        return new Queue(queueGratuidade, true);
    }
    
    @Bean
    public Queue fila2() {
        return new Queue(queueAgendamentoGratuidade, true);
    }

    @Bean
    public Binding binding1(Queue fila1, DirectExchange exchange) {
        return BindingBuilder.bind(fila1).to(exchange).with(this.queueGratuidade);
    }

    @Bean
    public Binding binding2(Queue fila2, DirectExchange exchange) {
        return BindingBuilder.bind(fila2).to(exchange).with(this.queueAgendamentoGratuidade);
    }

    @Bean
    @Override
    public RabbitTemplate getRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Override
    public String getQueueGratuidade() {
        return queueGratuidade;
    }

    @Override
    public String getQueueAgendamentoGratuidade() {
        return queueAgendamentoGratuidade;
    }
}