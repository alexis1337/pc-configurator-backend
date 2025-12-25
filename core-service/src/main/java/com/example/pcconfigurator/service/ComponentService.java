package com.example.pcconfigurator.service;

import com.example.pcconfigurator.domain.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComponentService {

    Page<Component> searchComponents(ComponentSearchCriteria criteria, Pageable pageable);

    Component getById(Long id);

    Component create(Component component);

    Component update(Long id, Component component);

    void delete(Long id);
}

