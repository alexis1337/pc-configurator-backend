package com.example.pcconfigurator.web.mapper;

import com.example.pcconfigurator.domain.BuildItemRole;
import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.PCBuildItem;
import com.example.pcconfigurator.domain.TargetUsage;
import com.example.pcconfigurator.web.dto.PCBuildItemRequestDto;
import com.example.pcconfigurator.web.dto.PCBuildItemResponseDto;
import com.example.pcconfigurator.web.dto.PCBuildRequestDto;
import com.example.pcconfigurator.web.dto.PCBuildResponseDto;

import java.util.ArrayList;
import java.util.List;

public final class PCBuildMapper {

    private PCBuildMapper() {
    }

    public static PCBuild toEntity(PCBuildRequestDto dto, List<Component> components) {
        PCBuild build = new PCBuild();
        build.setName(dto.getName());
        build.setTargetUsage(TargetUsage.valueOf(dto.getTargetUsage()));

        List<PCBuildItem> items = new ArrayList<>();
        for (int i = 0; i < dto.getItems().size(); i++) {
            PCBuildItemRequestDto itemDto = dto.getItems().get(i);
            Component component = components.get(i);

            PCBuildItem item = new PCBuildItem();
            item.setComponent(component);
            item.setQuantity(itemDto.getQuantity());
            item.setRole(BuildItemRole.valueOf(itemDto.getRole()));
            item.setBuild(build);
            items.add(item);
        }
        build.setItems(items);
        return build;
    }

    public static void updateEntity(PCBuild existing, PCBuildRequestDto dto, List<Component> components) {
        existing.setName(dto.getName());
        existing.setTargetUsage(TargetUsage.valueOf(dto.getTargetUsage()));

        existing.getItems().clear();
        for (int i = 0; i < dto.getItems().size(); i++) {
            PCBuildItemRequestDto itemDto = dto.getItems().get(i);
            Component component = components.get(i);

            PCBuildItem item = new PCBuildItem();
            item.setComponent(component);
            item.setQuantity(itemDto.getQuantity());
            item.setRole(BuildItemRole.valueOf(itemDto.getRole()));
            item.setBuild(existing);
            existing.getItems().add(item);
        }
    }

    // Обратное преобразование
    public static PCBuildResponseDto toDto(PCBuild build) {
        PCBuildResponseDto dto = new PCBuildResponseDto();
        dto.setId(build.getId());
        dto.setName(build.getName());
        dto.setTargetUsage(build.getTargetUsage().name());
        dto.setTotalPrice(build.getTotalPrice());
        dto.setCreatedAt(build.getCreatedAt());
        dto.setUpdatedAt(build.getUpdatedAt());

        List<PCBuildItemResponseDto> itemDtos = new ArrayList<>();
        for (PCBuildItem item : build.getItems()) {
            PCBuildItemResponseDto itemDto = new PCBuildItemResponseDto();
            itemDto.setId(item.getId());
            if (item.getComponent() != null) {
                itemDto.setComponentId(item.getComponent().getId());
                String title = "";
                if (item.getComponent().getVendor() != null) {
                    title += item.getComponent().getVendor() + " ";
                }
                if (item.getComponent().getModel() != null) {
                    title += item.getComponent().getModel();
                }
                itemDto.setComponentName(title.trim());
            }
            itemDto.setQuantity(item.getQuantity());
            itemDto.setRole(item.getRole().name());
            itemDtos.add(itemDto);
        }
        dto.setItems(itemDtos);
        return dto;
    }
}

