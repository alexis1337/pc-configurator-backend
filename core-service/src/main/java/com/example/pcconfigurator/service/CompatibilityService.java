package com.example.pcconfigurator.service;

import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.service.dto.CompatibilityIssue;

import java.util.List;

public interface CompatibilityService {

    List<CompatibilityIssue> checkBuildCompatibility(PCBuild build);

    List<CompatibilityIssue> checkComponentsCompatibility(List<Component> components);
}

