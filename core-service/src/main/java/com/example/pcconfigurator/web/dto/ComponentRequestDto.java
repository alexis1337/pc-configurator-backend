package com.example.pcconfigurator.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ComponentRequestDto {

    @NotNull
    private Long typeId;

    @NotBlank
    private String vendor;

    @NotBlank
    private String model;

    private BigDecimal price;

    private String description;

    private Boolean isAvailable;

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

