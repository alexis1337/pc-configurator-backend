package com.example.pcconfigurator.web;

import com.example.pcconfigurator.common.dto.CompatibilityIssueDto;
import com.example.pcconfigurator.common.dto.CompatibilityRpcRequestDto;
import com.example.pcconfigurator.common.dto.ComponentSnapshotDto;
import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.mapper.ComponentSnapshotMapper;
import com.example.pcconfigurator.messaging.CompatibilityRpcClient;
import com.example.pcconfigurator.repository.ComponentRepository;
import com.example.pcconfigurator.web.dto.CompatibilityCheckRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/compatibility")
@RequiredArgsConstructor
@Tag(name = "Compatibility", description = "Check component compatibility")
public class CompatibilityController {

    private final CompatibilityRpcClient compatibilityRpcClient;
    private final ComponentRepository componentRepository;

    @PostMapping("/check")
    @Operation(summary = "Check compatibility of a set of components")
    public List<CompatibilityIssueDto> checkCompatibility(
            @Valid @RequestBody CompatibilityCheckRequestDto request
    ) {
        List<Component> components = new ArrayList<>();
        for (Long id : request.getComponentIds()) {
            Component component = componentRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Component not found: " + id));
            components.add(component);
        }

        List<ComponentSnapshotDto> snapshots = components.stream()
                .map(ComponentSnapshotMapper::toSnapshot)
                .toList();

        CompatibilityRpcRequestDto mqRequest = new CompatibilityRpcRequestDto();
        mqRequest.setComponents(snapshots);

        return compatibilityRpcClient.check(mqRequest);
    }
}
