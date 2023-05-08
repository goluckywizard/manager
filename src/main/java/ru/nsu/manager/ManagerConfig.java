package ru.nsu.manager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerConfig {
    @Value("${queue.name}")
    String queueName;

    @Bean
    public Queue myQueue() {
        return new Queue(queueName);
    }
    @Bean
    public Queue responseQueue() {
        return new Queue("responseQueue");
    }
    @Bean
    DirectExchange exchange() {
        return new DirectExchange("directExchange");
    }
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2XmlMessageConverter();
    }
    @Bean
    Binding requestBinding(Queue responseQueue, DirectExchange exchange) {
        return BindingBuilder.bind(responseQueue).to(exchange).with("responseQueue");
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
//        rabbitTemplate.setExchange("directExchange");
        System.out.println("beaaaan");
        return rabbitTemplate;
    }
}
