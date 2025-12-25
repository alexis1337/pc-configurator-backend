package com.example.pcconfigurator.web.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

// DTO запроса проверки совместимости сборки
public class CompatibilityCheckRequestDto {

    @NotEmpty
    private List<Long> componentIds;

    public List<Long> getComponentIds() { return componentIds; }
    public void setComponentIds(List<Long> componentIds) { this.componentIds = componentIds; }
}
