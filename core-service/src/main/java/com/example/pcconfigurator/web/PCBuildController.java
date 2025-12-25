package com.example.pcconfigurator.web;

import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.User;
import com.example.pcconfigurator.repository.ComponentRepository;
import com.example.pcconfigurator.service.PCBuildService;
import com.example.pcconfigurator.service.UserService;
import com.example.pcconfigurator.web.dto.PCBuildRequestDto;
import com.example.pcconfigurator.web.dto.PCBuildResponseDto;
import com.example.pcconfigurator.web.mapper.PCBuildMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/builds")
@RequiredArgsConstructor
@Tag(name = "PC Builds", description = "Manage user PC builds")
public class PCBuildController {

    private final PCBuildService buildService;
    private final UserService userService;
    private final ComponentRepository componentRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "List builds of the current user")
    public Page<PCBuildResponseDto> listUserBuilds(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        User currentUser = userService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);
        Page<PCBuild> builds = buildService.getBuildsForUser(currentUser, pageable);

        List<PCBuildResponseDto> dtoList = builds.getContent().stream()
                .map(PCBuildMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, builds.getTotalElements());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a specific build of the current user")
    public PCBuildResponseDto getBuild(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        PCBuild build = buildService.getBuildForUser(currentUser, id);
        return PCBuildMapper.toDto(build);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new PC build for the current user")
    public PCBuildResponseDto createBuild(@Valid @RequestBody PCBuildRequestDto request) {
        User currentUser = userService.getCurrentUser();

        List<Component> components = new ArrayList<>();
        request.getItems().forEach(item -> {
            Component component = componentRepository.findById(item.getComponentId())
                    .orElseThrow(() -> new NoSuchElementException("Component not found: " + item.getComponentId()));
            components.add(component);
        });

        PCBuild build = PCBuildMapper.toEntity(request, components);
        build.setUser(currentUser);

        PCBuild saved = buildService.createBuild(build);
        return PCBuildMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update an existing PC build of the current user")
    public PCBuildResponseDto updateBuild(@PathVariable Long id,
                                          @Valid @RequestBody PCBuildRequestDto request) {
        User currentUser = userService.getCurrentUser();

        List<Component> components = new ArrayList<>();
        request.getItems().forEach(item -> {
            Component component = componentRepository.findById(item.getComponentId())
                    .orElseThrow(() -> new NoSuchElementException("Component not found: " + item.getComponentId()));
            components.add(component);
        });

        PCBuild existing = buildService.getBuildForUser(currentUser, id);
        PCBuildMapper.updateEntity(existing, request, components);

        PCBuild saved = buildService.updateBuild(currentUser, id, existing);
        return PCBuildMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a PC build of the current user")
    public void deleteBuild(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        buildService.deleteBuild(currentUser, id);
    }
}

