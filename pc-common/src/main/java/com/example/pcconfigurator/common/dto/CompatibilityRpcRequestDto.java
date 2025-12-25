package com.example.pcconfigurator.common.dto;

import java.util.List;

public class CompatibilityRpcRequestDto {
    private List<ComponentSnapshotDto> components;

    public List<ComponentSnapshotDto> getComponents() { return components; }
    public void setComponents(List<ComponentSnapshotDto> components) { this.components = components; }
}
