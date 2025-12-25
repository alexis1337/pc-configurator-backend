package com.example.pcconfigurator.repository;

import com.example.pcconfigurator.domain.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComponentRepository extends JpaRepository<Component, Long>, JpaSpecificationExecutor<Component> {
}

