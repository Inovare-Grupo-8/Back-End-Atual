package org.com.imaapi.infrastructure.config;

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
public class RabbitMQConfig {

    @Value("${email.queue.name:fila_email}")
    private String emailQueueName;
    
    @Value("${email.exchange.name:email}")
    private String emailExchangeName;
    
    @Value("${email.routing.key:email}")
    private String emailRoutingKey;
    
    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueueName, true); // durable = true
    }
    
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(emailExchangeName);
    }
    
    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange())
                .with(emailRoutingKey);
    }
    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}