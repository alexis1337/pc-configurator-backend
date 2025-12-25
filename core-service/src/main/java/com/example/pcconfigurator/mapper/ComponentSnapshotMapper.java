package com.example.pcconfigurator.mapper;

import com.example.pcconfigurator.common.dto.ComponentSnapshotDto;
import com.example.pcconfigurator.domain.Component;

// Преобразование сущности комплектухи в DTO
public class ComponentSnapshotMapper {

    public static ComponentSnapshotDto toSnapshot(Component component) {
        ComponentSnapshotDto dto = new ComponentSnapshotDto();

        dto.setTypeName(component.getType() != null ? component.getType().getName() : null);
        dto.setSocket(component.getSocket());
        dto.setRamType(component.getRamType());
        dto.setFormFactor(component.getFormFactor());
        dto.setGpuLength(component.getGpuLength());
        dto.setPsuPower(component.getPsuPower());
        dto.setTdp(component.getTdp());

        return dto;
    }
}
