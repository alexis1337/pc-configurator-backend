package com.example.pcconfigurator.service;

import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.TargetUsage;

import java.math.BigDecimal;
import java.util.Optional;

public interface RecommendationService {

    Optional<PCBuild> recommendBuild(BigDecimal budget, TargetUsage usage);
}

