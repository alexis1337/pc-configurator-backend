package com.example.pcconfigurator.web;

import com.example.pcconfigurator.domain.ComponentType;
import com.example.pcconfigurator.service.ComponentTypeService;
import com.example.pcconfigurator.web.dto.ComponentTypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/component-types")
@RequiredArgsConstructor
@Tag(name = "Component Types", description = "Static list of component types")
public class ComponentTypeController {

    private final ComponentTypeService componentTypeService;

    @GetMapping
    @Operation(summary = "List all component types (cached)")
    public List<ComponentTypeDto> listTypes() {
        List<ComponentType> types = componentTypeService.getAll();
        return types.stream()
                .map(type -> {
                    ComponentTypeDto dto = new ComponentTypeDto();
                    dto.setId(type.getId());
                    dto.setName(type.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

