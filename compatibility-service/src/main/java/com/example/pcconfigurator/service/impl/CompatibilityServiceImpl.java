package com.example.pcconfigurator.service.impl;

import com.example.pcconfigurator.common.dto.CompatibilityIssueDto;
import com.example.pcconfigurator.common.dto.ComponentSnapshotDto;
import com.example.pcconfigurator.service.CompatibilityService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompatibilityServiceImpl implements CompatibilityService {

    @Override
    public List<CompatibilityIssueDto> checkComponentsCompatibility(List<ComponentSnapshotDto> components) { // Принимает DTO
        List<CompatibilityIssueDto> issues = new ArrayList<>();

        ComponentSnapshotDto cpu = findFirstByTypeName(components, "CPU");
        ComponentSnapshotDto motherboard = findFirstByTypeName(components, "Motherboard");
        ComponentSnapshotDto ramModule = findFirstByTypeName(components, "RAM");
        ComponentSnapshotDto caseComponent = findFirstByTypeName(components, "Case");
        ComponentSnapshotDto gpu = findFirstByTypeName(components, "GPU");
        ComponentSnapshotDto psu = findFirstByTypeName(components, "PSU");

        // CPU - совместимость с МП
        if (cpu != null && motherboard != null) {
            if (notEqualsIgnoreCase(cpu.getSocket(), motherboard.getSocket())) {
                issues.add(new CompatibilityIssueDto(
                        "CPU_SOCKET_MISMATCH",
                        "CPU socket (" + nullSafe(cpu.getSocket()) + ") does not match motherboard socket (" +
                                nullSafe(motherboard.getSocket()) + ")."
                ));
            }
        }

        // MB - Совместимость с ОЗУ
        if (motherboard != null && ramModule != null) {
            if (notEqualsIgnoreCase(motherboard.getRamType(), ramModule.getRamType())) {
                issues.add(new CompatibilityIssueDto(
                        "RAM_TYPE_MISMATCH",
                        "Motherboard RAM type (" + nullSafe(motherboard.getRamType()) +
                                ") does not match RAM module type (" + nullSafe(ramModule.getRamType()) + ")."
                ));
            }
        }

        // Габариты платы
        if (caseComponent != null && motherboard != null) {
            if (notEqualsIgnoreCase(caseComponent.getFormFactor(), motherboard.getFormFactor())) {
                issues.add(new CompatibilityIssueDto(
                        "FORM_FACTOR_MISMATCH",
                        "Case form factor (" + nullSafe(caseComponent.getFormFactor()) +
                                ") may not support motherboard form factor (" +
                                nullSafe(motherboard.getFormFactor()) + ")."
                ));
            }
        }

        // Габариты видеокарты
        if (caseComponent != null && gpu != null &&
                caseComponent.getGpuLength() != null && gpu.getGpuLength() != null) {
            if (gpu.getGpuLength() > caseComponent.getGpuLength()) {
                issues.add(new CompatibilityIssueDto(
                        "GPU_TOO_LONG",
                        "GPU length (" + gpu.getGpuLength() + " mm) exceeds case maximum (" +
                                caseComponent.getGpuLength() + " mm)."
                ));
            }
        }

        // Мощность БП
        if (psu != null && psu.getPsuPower() != null) {
            int totalTdp = components.stream()
                    .map(ComponentSnapshotDto::getTdp)
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();

            // Рекомендуемая мощность
            int requiredPower = (int) Math.round(totalTdp * 1.2);
            if (psu.getPsuPower() < requiredPower) {
                issues.add(new CompatibilityIssueDto(
                        "PSU_POWER_INSUFFICIENT",
                        "PSU power (" + psu.getPsuPower() + " W) may be insufficient for total TDP (" +
                                totalTdp + " W). Recommended minimum is " + requiredPower + " W."
                ));
            }
        }

        return issues;
    }

    private ComponentSnapshotDto findFirstByTypeName(List<ComponentSnapshotDto> components, String typeName) {
        String expected = typeName.toLowerCase(Locale.ROOT);
        for (ComponentSnapshotDto c : components) {
            if (c.getTypeName() != null && c.getTypeName().toLowerCase(Locale.ROOT).equals(expected)) {
                return c;
            }
        }
        return null;
    }

    private boolean notEqualsIgnoreCase(String a, String b) {
        if (a == null || b == null) return true;
        return !a.equalsIgnoreCase(b);
    }

    private String nullSafe(String value) {
        return value != null ? value : "N/A";
    }
}
