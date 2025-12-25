package com.example.pcconfigurator.messaging;

import com.example.pcconfigurator.common.dto.RecommendationRequestDto;
import com.example.pcconfigurator.common.dto.RecommendationResponseDto;
import com.example.pcconfigurator.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RecommendationRpcClient {

    private final RabbitTemplate rabbitTemplate;

    public RecommendationRpcClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public RecommendationResponseDto recommend(RecommendationRequestDto request) {
        return (RecommendationResponseDto)
                rabbitTemplate.convertSendAndReceive(RabbitConfig.RECOMMENDATION_QUEUE, request);
    }
}
