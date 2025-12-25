package com.example.pcconfigurator.repository;

import com.example.pcconfigurator.domain.ComponentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComponentTypeRepository extends JpaRepository<ComponentType, Long> {

    Optional<ComponentType> findByNameIgnoreCase(String name);
}

