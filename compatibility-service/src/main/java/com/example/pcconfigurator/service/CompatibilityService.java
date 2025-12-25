package com.example.pcconfigurator.service;

import com.example.pcconfigurator.common.dto.CompatibilityIssueDto;
import com.example.pcconfigurator.common.dto.ComponentSnapshotDto;

import java.util.List;

public interface CompatibilityService {
    List<CompatibilityIssueDto> checkComponentsCompatibility(List<ComponentSnapshotDto> components);
}
