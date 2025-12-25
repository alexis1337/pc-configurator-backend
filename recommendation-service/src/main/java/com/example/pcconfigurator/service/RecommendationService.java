package com.example.pcconfigurator.service;

import com.example.pcconfigurator.common.dto.RecommendationRequestDto;
import com.example.pcconfigurator.common.dto.RecommendationResponseDto;

public interface RecommendationService {
    RecommendationResponseDto recommend(RecommendationRequestDto request);
}
