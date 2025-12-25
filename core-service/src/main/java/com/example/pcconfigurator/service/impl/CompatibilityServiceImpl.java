package com.example.pcconfigurator.service.impl;

import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.PCBuildItem;
import com.example.pcconfigurator.service.CompatibilityService;
import com.example.pcconfigurator.service.dto.CompatibilityIssue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class CompatibilityServiceImpl implements CompatibilityService {

    @Override
    public List<CompatibilityIssue> checkBuildCompatibility(PCBuild build) {
        List<Component> components = new ArrayList<>();
        for (PCBuildItem item : build.getItems()) {
            components.add(item.getComponent());
        }
        return checkComponentsCompatibility(components);
    }

    @Override
    public List<CompatibilityIssue> checkComponentsCompatibility(List<Component> components) {
        List<CompatibilityIssue> issues = new ArrayList<>();

        Component cpu = findFirstByTypeName(components, "CPU");
        Component motherboard = findFirstByTypeName(components, "Motherboard");
        Component ramModule = findFirstByTypeName(components, "RAM");
        Component caseComponent = findFirstByTypeName(components, "Case");
        Component gpu = findFirstByTypeName(components, "GPU");
        Component psu = findFirstByTypeName(components, "PSU");

        // CPU - совместимость с МП
        if (cpu != null && motherboard != null) {
            if (notEqualsIgnoreCase(cpu.getSocket(), motherboard.getSocket())) {
                issues.add(new CompatibilityIssue(
                        "CPU_SOCKET_MISMATCH",
                        "CPU socket (" + nullSafe(cpu.getSocket()) + ") does not match motherboard socket (" +
                                nullSafe(motherboard.getSocket()) + ")."
                ));
            }
        }

        // MB - Совместимость с ОЗУ
        if (motherboard != null && ramModule != null) {
            if (notEqualsIgnoreCase(motherboard.getRamType(), ramModule.getRamType())) {
                issues.add(new CompatibilityIssue(
                        "RAM_TYPE_MISMATCH",
                        "Motherboard RAM type (" + nullSafe(motherboard.getRamType()) +
                                ") does not match RAM module type (" + nullSafe(ramModule.getRamType()) + ")."
                ));
            }
        }

        // Габариты платы
        if (caseComponent != null && motherboard != null) {
            if (notEqualsIgnoreCase(caseComponent.getFormFactor(), motherboard.getFormFactor())) {
                issues.add(new CompatibilityIssue(
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
                issues.add(new CompatibilityIssue(
                        "GPU_TOO_LONG",
                        "GPU length (" + gpu.getGpuLength() + " mm) exceeds case maximum (" +
                                caseComponent.getGpuLength() + " mm)."
                ));
            }
        }

        // Мощность БП
        if (psu != null && psu.getPsuPower() != null) {
            int totalTdp = components.stream()
                    .map(Component::getTdp)
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();

            // Рекомендуемая мощность
            int requiredPower = (int) Math.round(totalTdp * 1.2);
            if (psu.getPsuPower() < requiredPower) {
                issues.add(new CompatibilityIssue(
                        "PSU_POWER_INSUFFICIENT",
                        "PSU power (" + psu.getPsuPower() + " W) may be insufficient for total TDP (" +
                                totalTdp + " W). Recommended minimum is " + requiredPower + " W."
                ));
            }
        }

        return issues;
    }

    private Component findFirstByTypeName(List<Component> components, String typeName) {
        String expected = typeName.toLowerCase(Locale.ROOT);
        for (Component c : components) {
            if (c.getType() != null && c.getType().getName() != null &&
                    c.getType().getName().toLowerCase(Locale.ROOT).equals(expected)) {
                return c;
            }
        }
        return null;
    }

    private boolean notEqualsIgnoreCase(String a, String b) {
        if (a == null || b == null) {
            return true;
        }
        return !a.equalsIgnoreCase(b);
    }

    private String nullSafe(String value) {
        return value != null ? value : "N/A";
    }
}

