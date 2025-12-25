package com.example.pcconfigurator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(
        name = "components",
        indexes = {
                @Index(name = "idx_components_type_price", columnList = "type_id, price"),
                @Index(name = "idx_components_socket", columnList = "socket"),
                @Index(name = "idx_components_ram_type", columnList = "ramType")
        }
)
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private ComponentType type;

    private String vendor;
    private String model;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private boolean isAvailable = true;

    private String socket;
    private String chipset;
    private String formFactor;
    private String ramType;
    private Integer capacity;
    private Integer frequency;
    private Integer tdp;
    private Integer gpuLength;
    private Integer psuPower;
}

