package com.example.pcconfigurator.service;

import lombok.Data;

import java.math.BigDecimal;

// Критерии поиска комплектующих
@Data
public class ComponentSearchCriteria {

    private String typeName;

    private BigDecimal priceMin;
    private BigDecimal priceMax;

    private String socket;
    private String ramType;
}

