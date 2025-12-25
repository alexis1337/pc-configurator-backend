package com.example.pcconfigurator.service;

import com.example.pcconfigurator.domain.ComponentType;

import java.util.List;

public interface ComponentTypeService {

    List<ComponentType> getAll();

    ComponentType getById(Long id);
}

