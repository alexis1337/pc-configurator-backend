package com.example.pcconfigurator.web.dto;

import lombok.Data;

@Data
public class PCBuildItemResponseDto {

    private Long id;
    private Long componentId;
    private String componentName;
    private int quantity;
    private String role;
}

