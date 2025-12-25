package com.example.pcconfigurator.service.impl;

import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.TargetUsage;
import com.example.pcconfigurator.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Override
    public Optional<PCBuild> recommendBuild(BigDecimal budget, TargetUsage usage) {
        // Заглушка, в будущем будет добавлен ML
        return Optional.empty();
    }
}

