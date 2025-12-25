package com.example.pcconfigurator.messaging;

import com.example.pcconfigurator.common.dto.RecommendationRequestDto;
import com.example.pcconfigurator.common.dto.RecommendationResponseDto;
import com.example.pcconfigurator.config.RabbitConfig;
import com.example.pcconfigurator.service.RecommendationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RecommendationRpcListener {

    private final RecommendationService recommendationService;

    public RecommendationRpcListener(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @RabbitListener(queues = RabbitConfig.RECOMMENDATION_QUEUE)
    public RecommendationResponseDto handle(RecommendationRequestDto request) {
        return recommendationService.recommend(request);
    }
}
