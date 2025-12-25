package com.example.pcconfigurator.service.impl;

import com.example.pcconfigurator.domain.Component;
import com.example.pcconfigurator.domain.ComponentType;
import com.example.pcconfigurator.repository.ComponentRepository;
import com.example.pcconfigurator.repository.ComponentTypeRepository;
import com.example.pcconfigurator.service.ComponentSearchCriteria;
import com.example.pcconfigurator.service.ComponentService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
// Управление комплектующими ПК (поиск, создание, обновление и удаление компонентов)
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentTypeRepository componentTypeRepository;

    @Override
    @Transactional(readOnly = true)
    // Поиск
    public Page<Component> searchComponents(ComponentSearchCriteria criteria, Pageable pageable) {
        Specification<Component> spec = Specification.where(null);

        if (criteria.getTypeName() != null && !criteria.getTypeName().isBlank()) {
            spec = spec.and((root, query, cb) -> {
                Join<Component, ComponentType> typeJoin =
                        root.join("type", JoinType.INNER);
                return cb.equal(cb.lower(typeJoin.get("name")),
                        criteria.getTypeName().toLowerCase());
            });
        }

        if (criteria.getPriceMin() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("price"), criteria.getPriceMin()));
        }
        if (criteria.getPriceMax() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("price"), criteria.getPriceMax()));
        }

        if (criteria.getSocket() != null && !criteria.getSocket().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("socket")), criteria.getSocket().toLowerCase()));
        }

        if (criteria.getRamType() != null && !criteria.getRamType().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("ramType")), criteria.getRamType().toLowerCase()));
        }

        return componentRepository.findAll(spec, pageable);
    }

    // Поиск по ID
    @Override
    @Transactional(readOnly = true)
    public Component getById(Long id) {
        return componentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Component not found: " + id));
    }

    // Создание
    @Override
    public Component create(Component component) {
        // Ensure type is managed if only ID is provided
        if (component.getType() != null && component.getType().getId() != null) {
            ComponentType type = componentTypeRepository.findById(component.getType().getId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Component type not found: " + component.getType().getId()));
            component.setType(type);
        }
        return componentRepository.save(component);
    }

    // Обновление
    @Override
    public Component update(Long id, Component updated) {
        Component existing = getById(id);

        existing.setType(updated.getType());
        existing.setVendor(updated.getVendor());
        existing.setModel(updated.getModel());
        existing.setPrice(updated.getPrice());
        existing.setDescription(updated.getDescription());
        existing.setAvailable(updated.isAvailable());

        existing.setSocket(updated.getSocket());
        existing.setChipset(updated.getChipset());
        existing.setFormFactor(updated.getFormFactor());
        existing.setRamType(updated.getRamType());
        existing.setCapacity(updated.getCapacity());
        existing.setFrequency(updated.getFrequency());
        existing.setTdp(updated.getTdp());
        existing.setGpuLength(updated.getGpuLength());
        existing.setPsuPower(updated.getPsuPower());

        return componentRepository.save(existing);
    }

    // Удаление
    @Override
    public void delete(Long id) {
        componentRepository.deleteById(id);
    }
}

