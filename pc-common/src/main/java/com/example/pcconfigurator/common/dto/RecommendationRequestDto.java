package com.example.pcconfigurator.common.dto;

import java.math.BigDecimal;

public class RecommendationRequestDto {
    private BigDecimal budget;
    private TargetUsage targetUsage;

    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }
    public TargetUsage getTargetUsage() { return targetUsage; }
    public void setTargetUsage(TargetUsage targetUsage) { this.targetUsage = targetUsage; }
}
