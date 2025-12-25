package com.example.pcconfigurator.repository;

import com.example.pcconfigurator.domain.PCBuild;
import com.example.pcconfigurator.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PCBuildRepository extends JpaRepository<PCBuild, Long> {

    Page<PCBuild> findByUser(User user, Pageable pageable);

    Optional<PCBuild> findByIdAndUser(Long id, User user);
}

