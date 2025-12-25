package com.example.pcconfigurator.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ComponentResponseDto {

    private Long id;
    private Long typeId;
    private String typeName;

    private String vendor;
    private String model;
    private BigDecimal price;
    private String description;
    private boolean isAvailable;

    private String socket;
    private String chipset;
    private String formFactor;
    private String ramType;
    private Integer capacity;
    private Integer frequency;
    private Integer tdp;
    private Integer gpuLength;
    private Integer psuPower;
}

