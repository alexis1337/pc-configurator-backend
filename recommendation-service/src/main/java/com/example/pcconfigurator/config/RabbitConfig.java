package com.example.pcconfigurator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String RECOMMENDATION_QUEUE = "recommendation.rpc";

    @Bean
    public Queue recommendationQueue() {
        return new Queue(RECOMMENDATION_QUEUE);
    }
}
