package com.example.pcconfigurator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pc_build_items")
public class PCBuildItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // К какой сборке относится комплектуха
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "build_id", nullable = false)
    private PCBuild build;

    // Само комплектующее
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    // Сколько его в сборке
    @Column(nullable = false)
    private int quantity = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BuildItemRole role = BuildItemRole.OTHER;
}

