package com.example.pcconfigurator.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PCBuildRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private String targetUsage;

    @NotNull
    private List<PCBuildItemRequestDto> items;
}

