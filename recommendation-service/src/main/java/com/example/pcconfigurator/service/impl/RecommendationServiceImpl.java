package com.example.pcconfigurator.service.impl;

import com.example.pcconfigurator.common.dto.RecommendationRequestDto;
import com.example.pcconfigurator.common.dto.RecommendationResponseDto;
import com.example.pcconfigurator.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Override
    public RecommendationResponseDto recommend(RecommendationRequestDto request) {
        RecommendationResponseDto response = new RecommendationResponseDto();
        response.setRecommendedComponents(Collections.emptyList()); // заглушка
        return response;
    }
}
