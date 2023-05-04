package ru.nsu.manager.config;

import org.springframework.amqp.core.Queue;
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
}
