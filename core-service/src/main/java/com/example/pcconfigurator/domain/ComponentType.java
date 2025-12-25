package com.example.pcconfigurator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "component_types")
public class ComponentType {

    // Уникальный идентификатор для комплектухи
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название типа комплектухи
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}

