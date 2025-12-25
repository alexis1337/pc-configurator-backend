package com.example.pcconfigurator.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PCBuildItemRequestDto {

    @NotNull
    private Long componentId;

    @Min(1)
    private int quantity = 1;

    @NotNull
    private String role;
}

