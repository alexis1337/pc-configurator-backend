package com.example.pcconfigurator.common.dto;

import java.util.List;

public class RecommendationResponseDto {
    private List<ComponentSnapshotDto> recommendedComponents;

    public List<ComponentSnapshotDto> getRecommendedComponents() {
        return recommendedComponents;
    }

    public void setRecommendedComponents(List<ComponentSnapshotDto> recommendedComponents) {
        this.recommendedComponents = recommendedComponents;
    }
}
