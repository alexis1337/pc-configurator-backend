package com.example.pcconfigurator.service.impl;

import com.example.pcconfigurator.domain.ComponentType;
import com.example.pcconfigurator.repository.ComponentTypeRepository;
import com.example.pcconfigurator.service.ComponentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComponentTypeServiceImpl implements ComponentTypeService {

    private final ComponentTypeRepository componentTypeRepository;

    @Override
    @Cacheable("componentTypes")
    public List<ComponentType> getAll() {
        return componentTypeRepository.findAll();
    }

    @Override
    public ComponentType getById(Long id) {
        return componentTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Component type not found: " + id));
    }
}

