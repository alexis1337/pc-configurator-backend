package com.example.pcconfigurator.web;

import com.example.pcconfigurator.common.dto.RecommendationRequestDto;
import com.example.pcconfigurator.common.dto.RecommendationResponseDto;
import com.example.pcconfigurator.messaging.RecommendationRpcClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Recommendations", description = "Generate recommended builds (via RabbitMQ)")
public class RecommendationController {

    private final RecommendationRpcClient recommendationRpcClient;

    public RecommendationController(RecommendationRpcClient recommendationRpcClient) {
        this.recommendationRpcClient = recommendationRpcClient;
    }

    @PostMapping
    @Operation(summary = "Get recommendations for budget and usage (RPC via RabbitMQ)")
    public RecommendationResponseDto recommend(@Valid @RequestBody RecommendationRequestDto request) {
        return recommendationRpcClient.recommend(request);
    }
}
