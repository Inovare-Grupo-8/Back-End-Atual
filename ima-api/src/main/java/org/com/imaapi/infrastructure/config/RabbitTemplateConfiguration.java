package org.com.imaapi.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitTemplateConfiguration {

    private final ConnectionFactory connectionFactory;

    @Value("${broker.exchange.name}")
    private String exchangeName;

    @Value("${broker.queue.name1}")
    private String fila1;

    @Value("${broker.queue.name2}")
    private String fila2;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue fila1() {
        return new Queue(fila1, true);
    }
    
    @Bean
    public Queue fila2() {
        return new Queue(fila2, true);
    }

    @Bean
    public Binding binding1(Queue fila1, DirectExchange exchange) {
        return BindingBuilder.bind(fila1).to(exchange).with(this.fila1);
    }

    @Bean
    public Binding binding2(Queue fila2, DirectExchange exchange) {
        return BindingBuilder.bind(fila2).to(exchange).with(this.fila2);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}