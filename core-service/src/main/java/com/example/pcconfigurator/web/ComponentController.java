package com.example.pcconfigurator.web;

import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.domain.ComponentType;
import com.example.pcconfigurator.repository.ComponentTypeRepository;
import com.example.pcconfigurator.service.ComponentSearchCriteria;
import com.example.pcconfigurator.service.ComponentService;
import com.example.pcconfigurator.web.dto.ComponentRequestDto;
import com.example.pcconfigurator.web.dto.ComponentResponseDto;
import com.example.pcconfigurator.web.mapper.ComponentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/components")
@RequiredArgsConstructor
@Tag(name = "Components", description = "Browse and manage PC components")
public class ComponentController {

    private final ComponentService componentService;
    private final ComponentTypeRepository componentTypeRepository;

    @GetMapping
    @Operation(summary = "List components with filters and pagination")
    public Page<ComponentResponseDto> listComponents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) @Parameter(description = "Component type name, e.g. CPU") String type,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) String ramType,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax
    ) {
        ComponentSearchCriteria criteria = new ComponentSearchCriteria();
        criteria.setTypeName(type);
        if (priceMin != null) {
            criteria.setPriceMin(java.math.BigDecimal.valueOf(priceMin));
        }
        if (priceMax != null) {
            criteria.setPriceMax(java.math.BigDecimal.valueOf(priceMax));
        }
        criteria.setSocket(socket);
        criteria.setRamType(ramType);

        Pageable pageable = PageRequest.of(page, size);
        Page<Component> components = componentService.searchComponents(criteria, pageable);

        List<ComponentResponseDto> dtoList = components.getContent().stream()
                .map(ComponentMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, components.getTotalElements());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get component by id")
    public ComponentResponseDto getById(@Parameter(description = "Component ID", required = true)
                                        @PathVariable("id") Long id) {
        Component component = componentService.getById(id);
        return ComponentMapper.toDto(component);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new component (ADMIN only)")
    public ComponentResponseDto create(@Valid @RequestBody ComponentRequestDto request) {
        ComponentType type = componentTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new NoSuchElementException("Component type not found: " + request.getTypeId()));

        Component entity = ComponentMapper.toEntity(request, type);
        Component saved = componentService.create(entity);
        return ComponentMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a component (ADMIN only)")
    public ComponentResponseDto update(@PathVariable Long id,
                                       @Valid @RequestBody ComponentRequestDto request) {
        Component existing = componentService.getById(id);
        ComponentType type = componentTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new NoSuchElementException("Component type not found: " + request.getTypeId()));

        ComponentMapper.updateEntity(existing, request, type);
        Component saved = componentService.update(id, existing);
        return ComponentMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a component (ADMIN only)")
    public void delete(@PathVariable Long id) {
        componentService.delete(id);
    }
}

