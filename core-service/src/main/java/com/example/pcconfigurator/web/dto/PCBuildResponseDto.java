package com.example.pcconfigurator.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class PCBuildResponseDto {

    private Long id;
    private String name;
    private String targetUsage;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private Instant updatedAt;
    private List<PCBuildItemResponseDto> items;
}

