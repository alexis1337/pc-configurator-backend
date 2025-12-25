package com.example.pcconfigurator.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Отображение ошибки несовместимости
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompatibilityIssue {

    private String code;
    private String message;
}

