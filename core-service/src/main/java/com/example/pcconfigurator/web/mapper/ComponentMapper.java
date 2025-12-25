package com.example.pcconfigurator.web.mapper;

import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.domain.ComponentType;
import com.example.pcconfigurator.web.dto.ComponentRequestDto;
import com.example.pcconfigurator.web.dto.ComponentResponseDto;

// Маппер для изоляции внутренней модели от внешнего API
public final class ComponentMapper {

    private ComponentMapper() {
    }

    public static Component toEntity(ComponentRequestDto dto, ComponentType type) {
        Component entity = new Component();
        entity.setType(type);
        entity.setVendor(dto.getVendor());
        entity.setModel(dto.getModel());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setAvailable(dto.getIsAvailable() == null || dto.getIsAvailable());
        entity.setSocket(dto.getSocket());
        entity.setChipset(dto.getChipset());
        entity.setFormFactor(dto.getFormFactor());
        entity.setRamType(dto.getRamType());
        entity.setCapacity(dto.getCapacity());
        entity.setFrequency(dto.getFrequency());
        entity.setTdp(dto.getTdp());
        entity.setGpuLength(dto.getGpuLength());
        entity.setPsuPower(dto.getPsuPower());
        return entity;
    }

    public static void updateEntity(Component entity, ComponentRequestDto dto, ComponentType type) {
        entity.setType(type);
        entity.setVendor(dto.getVendor());
        entity.setModel(dto.getModel());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setAvailable(dto.getIsAvailable() == null || dto.getIsAvailable());
        entity.setSocket(dto.getSocket());
        entity.setChipset(dto.getChipset());
        entity.setFormFactor(dto.getFormFactor());
        entity.setRamType(dto.getRamType());
        entity.setCapacity(dto.getCapacity());
        entity.setFrequency(dto.getFrequency());
        entity.setTdp(dto.getTdp());
        entity.setGpuLength(dto.getGpuLength());
        entity.setPsuPower(dto.getPsuPower());
    }

    public static ComponentResponseDto toDto(Component entity) {
        ComponentResponseDto dto = new ComponentResponseDto();
        dto.setId(entity.getId());

        if (entity.getType() != null) {
            dto.setTypeId(entity.getType().getId());
            dto.setTypeName(entity.getType().getName());
        }

        dto.setVendor(entity.getVendor());
        dto.setModel(entity.getModel());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setAvailable(entity.isAvailable());
        dto.setSocket(entity.getSocket());
        dto.setChipset(entity.getChipset());
        dto.setFormFactor(entity.getFormFactor());
        dto.setRamType(entity.getRamType());
        dto.setCapacity(entity.getCapacity());
        dto.setFrequency(entity.getFrequency());
        dto.setTdp(entity.getTdp());
        dto.setGpuLength(entity.getGpuLength());
        dto.setPsuPower(entity.getPsuPower());
        return dto;
    }
}

