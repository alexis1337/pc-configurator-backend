package com.example.pcconfigurator.repository;

import com.example.pcconfigurator.domain.PCBuildItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PCBuildItemRepository extends JpaRepository<PCBuildItem, Long> {
}

